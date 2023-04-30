import edu.tartu.esi.Payment;
import edu.tartu.esi.PaymentDTO;
import edu.tartu.esi.PaymentMethod;
import edu.tartu.esi.PaymentRepository;
import edu.tartu.esi.PaymentMapper;
import edu.tartu.esi.PaymentStatus;
import edu.tartu.esi.UserRequestMessage;
import edu.tartu.esi.UserResponseMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<String, PaymentDto> paymentDtoKafkaTemplate;
    private final String paymentTopic;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository,
                          KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate,
                          KafkaTemplate<String, PaymentDto> paymentDtoKafkaTemplate,
                          String paymentTopic,
                          PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
        this.paymentTopic = paymentTopic;
        this.paymentMapper = paymentMapper;
    }

    public void makePayment(UUID payerId, UUID receiverId, UUID bookingId, double amount) {
        // Get payment method for user
        PaymentMethod paymentMethod = getPaymentMethodForUser(payerId);

        // Update the balance and create the payment
        double newBalance = Double.parseDouble(paymentMethod.getBalance()) - amount;
        paymentMethod.setBalance(String.valueOf(newBalance));

        Payment payment = new Payment(UUID.randomUUID(), payerId, receiverId, bookingId, LocalDateTime.now(), amount, PaymentStatus.PENDING);
        paymentRepository.save(payment);

        // Publish the payment result to the Kafka topic
        PaymentDTO paymentDto = PaymentMapper.INSTANCE.paymentToPaymentDto(payment);
        paymentDtoKafkaTemplate.send(paymentTopic, paymentDto);
    }

    private PaymentMethod getPaymentMethodForUser(UUID userId) {
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
        PaymentMethod paymentMethod = paymentMethodResponses.get(requestId);
        paymentMethodResponses.remove(requestId);
        return paymentMethod;
    }

    @KafkaListener(topics = "user-response", groupId = "paymentServiceGroup")
    public void onUserResponse(UserResponseMessage message) {
        paymentMethodResponses.put(message.getRequestId(), message.getPaymentMethod());
        latch.countDown();
    }
}
