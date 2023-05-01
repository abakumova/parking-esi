package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    public AnalyticsDTO getAnalyticsByParkingSlotId(String parkingSlotId) throws ExecutionException, InterruptedException {
        // Send analytics request for the parking slot ID
        kafkaProducerService.sendAnalyticsRequest(parkingSlotId);

        // Wait for the response and return the result
        CompletableFuture<AnalyticsDTO> analyticsFuture = kafkaConsumerService.getAnalyticsResponse(parkingSlotId);
        return analyticsFuture.get();
    }

    public List<AnalyticsDTO> getAnalyticsByParkingSlotIds(List<String> parkingSlotIds) throws ExecutionException, InterruptedException {
        // Send analytics requests for all parking slot IDs
        parkingSlotIds.forEach(kafkaProducerService::sendAnalyticsRequest);

        // Wait for the responses and collect the results
        List<CompletableFuture<AnalyticsDTO>> analyticsFutures = parkingSlotIds.stream()
                .map(kafkaConsumerService::getAnalyticsResponse)
                .toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(analyticsFutures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<AnalyticsDTO>> allAnalytics = allFutures.thenApply(future -> analyticsFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        return allAnalytics.get();
    }
}
