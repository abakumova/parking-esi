package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;
import java.lang.String;

@Service
public class PaymentService {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final ConcurrentMap<String, PaymentMethodDTO> paymentMethodDTOResponses = new ConcurrentHashMap<>();
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<Object, PaymentDTO> paymentDtoKafkaTemplate;
    @Value("${kafka.topics.payment}")
    private String paymentTopic;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository,
                          KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate,
                          KafkaTemplate<Object, PaymentDTO> paymentDtoKafkaTemplate,
                          String paymentTopic,
                          PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
        this.paymentTopic = paymentTopic;
        this.paymentMapper = paymentMapper;
    }

    public void makePayment(UUID payerId, UUID receiverId, UUID bookingId, BigDecimal amount) {
        // Get payment method for user
        PaymentMethodDTO PaymentMethodDTO = getPaymentMethodDTOForUser(payerId);

        // Update the balance and create the payment
        BigDecimal newBalance = new BigDecimal(PaymentMethodDTO.getBalance()).subtract(amount);
        PaymentMethodDTO.setBalance(String.valueOf(newBalance));

        Payment payment = new Payment(UUID.randomUUID(), payerId, receiverId, bookingId, amount, Payment.PaymentStatus.PENDING, LocalDateTime.now());
        paymentRepository.save(payment);

        // Publish the payment result to the Kafka topic
        PaymentDTO paymentDto = paymentMapper.toDto(payment);
        paymentDtoKafkaTemplate.send(paymentTopic, paymentDto);
    }

    private PaymentMethodDTO getPaymentMethodDTOForUser(UUID userId) {
        // Send a request to the user-management service
        String requestId = UUID.randomUUID().toString();
        UserRequestMessage requestMessage = new UserRequestMessage(requestId, userId.toString());
        userRequestKafkaTemplate.send("user-request", requestMessage);

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
