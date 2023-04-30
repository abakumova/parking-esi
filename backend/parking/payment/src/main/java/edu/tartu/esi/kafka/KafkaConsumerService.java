package edu.tartu.esi.kafka;

import edu.tartu.esi.dto.PaymentDto;
import edu.tartu.esi.kafka.message.UserRequestMessage;
import edu.tartu.esi.kafka.message.UserResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private final KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate;
    private final KafkaTemplate<Object, PaymentDto> paymentDtoKafkaTemplate;
    @Value("${kafka.topics.payment}")
    private String paymentTopic;

    public KafkaConsumerService(KafkaTemplate<String, UserRequestMessage> userRequestKafkaTemplate, KafkaTemplate<Object, PaymentDto> paymentDtoKafkaTemplate) {
        this.userRequestKafkaTemplate = userRequestKafkaTemplate;
        this.paymentDtoKafkaTemplate = paymentDtoKafkaTemplate;
    }

    @KafkaListener(topics = "user-response", groupId = "payment-group")
    public void listen(@Payload UserResponseMessage message) {
        log.debug("-- Message is received {}", message.toString());
//        UserDto userDto = userService.getUserById(message.getUserId());
//
//        UserResponseMessage responseMessage = new UserResponseMessage();
//        responseMessage.setRequestId(message.getRequestId());
//        responseMessage.setUserId(userDto.getId());
//        responseMessage.setPaymentMethod(userDto.getPaymentMethod());
//
//        log.debug("-- Message is formed {}", responseMessage);
//        kafkaTemplate.send("user-response", responseMessage);
    }
}
