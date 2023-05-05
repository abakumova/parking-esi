package edu.tartu.esi.service;

import edu.tartu.esi.kafka.message.UserBalanceMessage;
import edu.tartu.esi.kafka.message.UserRequestMessage;
import edu.tartu.esi.mapper.PaymentMapper;
import edu.tartu.esi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class PaymentService {

    //private final ConcurrentMap<String, PaymentMethodDto> paymentMethodDto = new ConcurrentHashMap<>();
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate, KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
    }

    @KafkaListener(topics = "balance-topic")
    public void makePayment() {

        String paymentMethodDto = getPaymentMethodDtoForUser(userId);

//        // Update the balance and create the payment
//        BigDecimal newBalance = new BigDecimal(paymentMethodDto.getBalance()).subtract(amount);
//        paymentMethodDto.setBalance(String.valueOf(newBalance));
//
//        Payment payment = new Payment(UUID.randomUUID().toString(), payerId, receiverId, bookingId, amount, Payment.PaymentStatus.PENDING, LocalDateTime.now());
//        paymentRepository.save(payment);
//
//        // Publish the payment result to the Kafka topic
//        PaymentDto paymentDto = paymentMapper.toDto(payment);
//        // requestId, userId, balance
//        UserBalanceMessage userBalanceMessage = new UserBalanceMessage();
//        userBalanceMessage.setRequestId("nfnnf");
//        userBalanceMessage.setUserId(payerId);userBalanceMessage.setBalance("10");
        paymentDtoKafkaTemplate.send("balance-topic", userBalanceMessage);
    }

    public String getPaymentMethodDtoForUser(String userId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8083/v1/api/users/{id}/balance", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
