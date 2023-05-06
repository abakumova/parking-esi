package edu.tartu.esi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {

//    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void startSendingMessages() {
//        scheduler.scheduleAtFixedRate(() -> {
//            // create a message to send
//            String message = "Hello, Kafka!";
//
//            // send the message to the "my-topic" topic
//            kafkaTemplate.send("my-topic", message);
//        }, 0, 10, TimeUnit.SECONDS); // send a message every 10 seconds
//    }
//
//    public void stopSendingMessages() {
//        scheduler.shutdown();
//    }

}