package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationEventProducer {

    @Autowired
    private KafkaTemplate<String, LocationEvent> kafkaTemplate;

    @Value("${location.topic.name}")
    private String locationTopic;

//    public void sendLocationEvent(LocationEvent locationEvent) {
//        kafkaTemplate.send(locationTopic, locationEvent);
//    }
}
