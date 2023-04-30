package edu.tartu.esi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;
import java.lang.String;

@Service
@Slf4j
public class PaymentService {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final ConcurrentMap<String, PaymentMethodDTO> paymentMethodDTOResponses = new ConcurrentHashMap<>();
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate, KafkaTemplate<String, UserBalanceMessage> paymentDtoKafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
    }

    public void makePayment(String payerId, String receiverId, String bookingId, BigDecimal amount) {
        // Get payment method for user
        PaymentMethodDTO PaymentMethodDTO = getPaymentMethodDTOForUser(payerId);

        // Update the balance and create the payment
        BigDecimal newBalance = new BigDecimal(PaymentMethodDTO.getBalance()).subtract(amount);
        PaymentMethodDTO.setBalance(String.valueOf(newBalance));

        Payment payment = new Payment(UUID.randomUUID().toString(), payerId, receiverId, bookingId, amount, Payment.PaymentStatus.PENDING, LocalDateTime.now());
        paymentRepository.save(payment);

        // Publish the payment result to the Kafka topic
        PaymentDTO paymentDto = paymentMapper.toDto(payment);
        // requestId, userId, balance
        UserBalanceMessage userBalanceMessage = new UserBalanceMessage();
        userBalanceMessage.setRequestId("nfnnf");
        userBalanceMessage.setUserId(payerId);userBalanceMessage.setBalance("10");
        paymentDtoKafkaTemplate.send("balance-request", userBalanceMessage);
    }

    PaymentMethodDTO getPaymentMethodDTOForUser(String userId) {
        // Send a request to the user-management service
        String requestId = UUID.randomUUID().toString();
        UserRequestMessage requestMessage = new UserRequestMessage(requestId, userId);
        userRequestKafkaTemplate.send("user-request-topic", requestMessage);

        // Wait for the response
        try {
            latch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retrieve the payment method from the response
        PaymentMethodDTO PaymentMethodDTO = paymentMethodDTOResponses.get(requestId);
        paymentMethodDTOResponses.remove(requestId);
        return PaymentMethodDTO;
    }

    @KafkaListener(topics = "user-response", groupId = "paymentServiceGroup")
    public void onUserResponse(UserResponseMessage message) {
        paymentMethodDTOResponses.put(message.getRequestId(), message.getPaymentMethodDTO());
        latch.countDown();
    }
}
