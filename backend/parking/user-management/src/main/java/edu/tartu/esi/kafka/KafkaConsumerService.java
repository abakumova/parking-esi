package edu.tartu.esi.kafka;

import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.kafka.message.UserBalanceMessage;
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

    @KafkaListener(topics = "user-request-topic", groupId = "user-group")
    public void listenUser(@Payload UserRequestMessage message) {
        log.warn("-- Message is received {}", message.toString());
        UserDto userDto = userService.getUserById(message.getUserId());
        log.warn("-- user {}", userDto);
        UserResponseMessage responseMessage = new UserResponseMessage(message.getRequestId(), userDto.getId(), userDto.getPaymentMethod());
        log.warn("-- Message is formed {}", responseMessage);
        kafkaTemplate.send("user-response-topic", responseMessage);
    }

    @KafkaListener(topics = "balance-request", groupId = "user-group")
    public void listenBalance(@Payload UserBalanceMessage message) {
        log.warn("-- Message is received {}", message.toString());
        UserDto userDto = userService.getUserById(message.getUserId());
        userDto.getPaymentMethod().setBalance(message.getBalance());
        userService.updateUser(message.getUserId(), userDto);
        log.warn("-- User was updated {}", userDto);
    }
}
