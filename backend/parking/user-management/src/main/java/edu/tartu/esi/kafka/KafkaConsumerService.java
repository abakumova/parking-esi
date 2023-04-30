package edu.tartu.esi.kafka;

import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.kafka.message.UserRequestMessage;
import edu.tartu.esi.kafka.message.UserResponseMessage;
import edu.tartu.esi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private UserService userService;
    private final KafkaTemplate<String, UserResponseMessage> kafkaTemplate;

    public KafkaConsumerService(UserService userService, KafkaTemplate<String, UserResponseMessage> kafkaTemplate) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "user-request", groupId = "myGroup")
    public void listen(@Payload UserRequestMessage message) {
        log.debug("-- Message is received {}", message.toString());
        UserDto userDto = userService.getUserById(message.getUserId());

        UserResponseMessage responseMessage = new UserResponseMessage();
        responseMessage.setRequestId(message.getRequestId());
        responseMessage.setUserId(userDto.getId());
        responseMessage.setPaymentMethod(userDto.getPaymentMethod());

        log.debug("-- Message is formed {}", responseMessage);
        kafkaTemplate.send("user-response", responseMessage);
    }
}
