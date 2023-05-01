package edu.tartu.esi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.analytics-request}")
    private String analyticsRequestTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendAnalyticsRequest(String parkingSlotId) {
        AnalyticsRequest analyticsRequest = AnalyticsRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .parkingSlotId(parkingSlotId)
                .build();

        try {
            String message = objectMapper.writeValueAsString(analyticsRequest);
            kafkaTemplate.send(analyticsRequestTopic, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing analytics request", e);
        }
    }
}
