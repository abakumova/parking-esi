package edu.tartu.esi.kafka;

import edu.tartu.esi.kafka.message.BookingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
//    private final KafkaTemplate<String, BookingMessage> bookingMessageKafkaTemplate;
//
//    public KafkaConsumerService(KafkaTemplate<String, BookingMessage> bookingMessageKafkaTemplate) {
//        this.bookingMessageKafkaTemplate = bookingMessageKafkaTemplate;
//    }

//    @KafkaListener(topics = "booking-topic", groupId = "booking-group")
//    public void listen(@Payload BookingMessage message) {
//        log.debug("-- Message is received {}", message.toString());
////        UserDto userDto = userService.getUserById(message.getUserId());
////
////        UserResponseMessage responseMessage = new UserResponseMessage();
////        responseMessage.setRequestId(message.getRequestId());
////        responseMessage.setUserId(userDto.getId());
////        responseMessage.setPaymentMethod(userDto.getPaymentMethod());
////
////        log.debug("-- Message is formed {}", responseMessage);
////        kafkaTemplate.send("user-response", responseMessage);
//    }
    @KafkaListener(topics = "booking-topic")
    public void consumeBookingMessage(BookingMessage bookingMessage, Acknowledgment acknowledgment) {
        // Process the booking message here
        System.out.println("Received booking message: " + bookingMessage);

        // Acknowledge the message
        acknowledgment.acknowledge();
    }
}
