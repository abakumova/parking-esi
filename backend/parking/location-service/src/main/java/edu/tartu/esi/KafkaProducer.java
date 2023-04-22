package edu.tartu.esi;

import edu.tartu.esi.LocationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, LocationEvent> kafkaTemplate;

    @Value("${kafka.location.topic}")
    private String locationTopic;

    public void sendLocationEvent(LocationEvent locationEvent) {
        kafkaTemplate.send(locationTopic, locationEvent);
    }
}
