package edu.tartu.esi.service;

import edu.tartu.esi.mapper.PaymentEntityMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.model.Payment;
import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
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
            updateBalance(booking.getCustomerId(), newBalance.toString());

            paymentStatus = PaymentStatusEnum.COMPLETED;

            BigDecimal balanceLandlord = new BigDecimal(getPaymentMethodDtoForUser(booking.getLandlordId()));
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
    public void updateBalance(String userId, String newBalanceStr) throws JSONException {
        Map<String, String> jwtTokenMap = getToken(email, password);

        webClientBuilder.build()
                .put()
                .uri("http://localhost:8089/api/v1/users/" + userId + "/balance")
                .header("Authorization", "Bearer " + jwtTokenMap.get("access_token"))
                .bodyValue(newBalanceStr)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @LoadBalanced
    public Booking getBooking(String bookingId) throws JSONException {
        Map<String, String> jwtTokenMap = getToken(email, password);
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8089/api/v1/bookings/" + bookingId)
                .header("Authorization", "Bearer " + jwtTokenMap.get("access_token"))
                .retrieve()
                .bodyToMono(Booking.class)
                .block();
    }

    @LoadBalanced
    public String getPaymentMethodDtoForUser(String userId) throws JSONException {
        Map<String, String> jwtTokenMap = getToken(email, password);
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8089/api/v1/users/" + userId + "/balance")
                .header("Authorization", "Bearer " + jwtTokenMap.get("access_token"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private  Map<String, String> getToken(String email, String password) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("password", password);

        Mono<Map<String, String>> jwtTokenMono = webClientBuilder.build().post()
                .uri("/api/v1/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {});

        return jwtTokenMono.block();
    }
}
