package edu.tartu.esi.service;

import edu.tartu.esi.dto.PaymentMethodDto;
import edu.tartu.esi.kafka.message.UserBalanceMessage;
import edu.tartu.esi.kafka.message.UserRequestMessage;
import edu.tartu.esi.kafka.message.UserPaymentMethodResponseMessage;
import edu.tartu.esi.mapper.PaymentMapper;
import edu.tartu.esi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentService {

    private final CountDownLatch latch = new CountDownLatch(1);
    //private final ConcurrentMap<String, PaymentMethodDto> paymentMethodDto = new ConcurrentHashMap<>();
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

//    public void makePayment(String payerId, String receiverId, String bookingId, BigDecimal amount) {
//        // Get payment method for user
//        PaymentMethodDto paymentMethodDto = getPaymentMethodDtoForUser(payerId);
//
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
//        paymentDtoKafkaTemplate.send("balance-request", userBalanceMessage);
//    }

    @KafkaListener(topics = "user-response-topic", groupId = "payment-group",
            containerFactory = "userRequestMessageListenerContainerFactory",
            id = "user-message-listener")
    public PaymentMethodDto getPaymentMethodDtoForUser(@Payload UserPaymentMethodResponseMessage message) {
        String requestId = UUID.randomUUID().toString();
        String userId = message.getUserId();
        UserRequestMessage requestMessage = new UserRequestMessage(requestId, userId);
        userRequestKafkaTemplate.send("user-request-topic", requestMessage);

        try {
            latch.await(15, TimeUnit.SECONDS);
            if (userId.equals(message.getUserId())) {
                return message.getPaymentMethodDTO();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
