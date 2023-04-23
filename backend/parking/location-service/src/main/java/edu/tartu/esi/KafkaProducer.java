package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private StreamBridge streamBridge;

    public void sendLocationEvent(LocationEvent locationEvent) {
        streamBridge.send("locationEvent-out-0", locationEvent);
    }
}
