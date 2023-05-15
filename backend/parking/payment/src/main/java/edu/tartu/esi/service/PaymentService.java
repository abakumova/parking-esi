package edu.tartu.esi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tartu.esi.mapper.PaymentEntityMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.model.Payment;
import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    private PaymentEntityMapper paymentMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${webclient.email}")
    private String email;
    @Value("${webclient.password}")
    private String password;

    public PaymentStatusEnum makePayment(String bookingId) throws JSONException {
        Booking booking = getBooking(bookingId);
        log.warn("Booking entity {}", booking);
        String oldBalanceStr = getPaymentMethodDtoForUser(booking.getCustomerId());
        PaymentStatusEnum paymentStatus;

        Duration duration = Duration.between(booking.getTimeFrom(), booking.getTimeUntil());
        BigDecimal hours = new BigDecimal(duration.toHours());

        BigDecimal oldBalance = new BigDecimal(oldBalanceStr);
        String pricePerHour = booking.getPrice();
        BigDecimal price = new BigDecimal(pricePerHour);
        BigDecimal amount = price.multiply(hours);

        if (oldBalance.compareTo(amount) >= 0) {
            BigDecimal newBalance = oldBalance.subtract(amount);
            log.warn("PAYMENT 1");
            updateBalance(booking.getCustomerId(), newBalance.toString());
            log.warn("PAYMENT 2");
            paymentStatus = PaymentStatusEnum.COMPLETED;
            log.warn("PAYMENT 3");
            BigDecimal balanceLandlord = new BigDecimal(getPaymentMethodDtoForUser(booking.getLandlordId()));
            log.warn("PAYMENT 3");
            updateBalance(booking.getLandlordId(), balanceLandlord.add(amount).toString());
        } else {
            paymentStatus = PaymentStatusEnum.DECLINED;
        }

        Payment payment = Payment.builder()
                .payerId(booking.getCustomerId())
                .receiverId(booking.getLandlordId())
                .bookingId(bookingId)
                .time(LocalDateTime.now())
                .amount(amount)
                .status(paymentStatus)
                .build();

        paymentRepository.save(payment);

        return paymentStatus;
    }


    @LoadBalanced
    @SneakyThrows
    public void updateBalance(String userId, String newBalanceStr) {
        log.warn("updateBalance 1");
        String token = getToken(email, password);
        log.warn("updateBalance 2 token {}", token);
        String urlBuilder = "http://localhost:8089/api/v1/users/" +
                userId +
                "/balance";
        webClientBuilder.build()
                .put()
                .uri(urlBuilder)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(newBalanceStr)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @LoadBalanced
    @SneakyThrows
    public Booking getBooking(String bookingId) {
        log.warn("getBooking 1");
        String token = getToken(email, password);
        log.warn("getBooking 2 token {}", token);
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8089/api/v1/bookings/" + bookingId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Booking.class)
                .block();
    }

    @LoadBalanced
    @SneakyThrows
    public String getPaymentMethodDtoForUser(String userId) {
        log.warn("getPaymentMethodDtoForUser 1");
        String token = getToken(email, password);
        log.warn("getPaymentMethodDtoForUser 2 token {}", token);
        String urlBuilder = "http://localhost:8089/api/v1/users/" +
                userId +
                "/balance";
        return webClientBuilder
                .build()
                .get()
                .uri(urlBuilder)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @SneakyThrows
    private String getToken(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", json);

        JsonNode jwtTokenMono = webClientBuilder
                .build()
                .post()
                .uri("http://localhost:8089/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(s -> s.path("access_token"))
                .block();

        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", jwtTokenMono);
        return jwtTokenMono.toString();
    }
}
