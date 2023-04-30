package edu.tartu.esi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LocationEventProducer {

    @Autowired
    private KafkaTemplate<String, LocationMessage> kafkaTemplate;

    @Value("${location.topic.name}")
    private String locationTopic;

    public void sendLocationEvent(LocationMessage locationMessage) {
        log.warn("Message {}", locationMessage);
        kafkaTemplate.send(locationTopic, locationMessage);
    }
}
