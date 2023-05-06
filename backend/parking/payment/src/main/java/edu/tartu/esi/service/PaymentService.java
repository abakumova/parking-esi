package edu.tartu.esi.service;

import edu.tartu.esi.kafka.message.UserBalanceMessage;
import edu.tartu.esi.kafka.message.UserRequestMessage;
import edu.tartu.esi.mapper.PaymentMapper;
import edu.tartu.esi.model.Booking;
import edu.tartu.esi.model.Payment;
import edu.tartu.esi.model.PaymentStatusEnum;
import edu.tartu.esi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class PaymentService {

    //private final ConcurrentMap<String, PaymentMethodDto> paymentMethodDto = new ConcurrentHashMap<>();
    private static PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    private static WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate, KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
    }


    public static PaymentStatusEnum makePayment(String bookingId) {
        Booking booking = getBooking(bookingId);

        String oldBalanceStr = getPaymentMethodDtoForUser(booking.getCustomerId());
        String amountStr = booking.getPrice();
        PaymentStatusEnum paymentStatus;

        BigDecimal oldBalance = new BigDecimal(oldBalanceStr);
        BigDecimal amount = new BigDecimal(amountStr);

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


    public static void updateBalance(String userId, String newBalanceStr) {
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/v1/users/{id}/balance", userId)
                .bodyValue(newBalanceStr)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public static Booking getBooking(String bookingId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8086/api/v1/bookings/{id}", bookingId)
                .retrieve()
                .bodyToMono(Booking.class)
                .block();
    }

    public static String getPaymentMethodDtoForUser(String userId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8083/api/v1/users/{id}/balance", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
