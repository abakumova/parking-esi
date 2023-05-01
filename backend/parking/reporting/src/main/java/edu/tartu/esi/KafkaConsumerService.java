package edu.tartu.esi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConcurrentHashMap<String, CompletableFuture<AnalyticsDTO>> responseFutures = new ConcurrentHashMap<>();

    @KafkaListener(topics = "${kafka.topic.analytics-response}", groupId = "${kafka.group-id}")
    public void processAnalyticsResponse(String message) {
        try {
            AnalyticsDTO analyticsDTO = objectMapper.readValue(message, AnalyticsDTO.class);
            String requestId = analyticsDTO.getAnalyticsId();
            CompletableFuture<AnalyticsDTO> future = responseFutures.get(requestId);

            if (future != null) {
                future.complete(analyticsDTO);
                responseFutures.remove(requestId);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing analytics response", e);
        }
    }

    public CompletableFuture<AnalyticsDTO> getAnalyticsResponse(String requestId) {
        CompletableFuture<AnalyticsDTO> future = new CompletableFuture<>();
        responseFutures.put(requestId, future);
        return future;
    }
}
