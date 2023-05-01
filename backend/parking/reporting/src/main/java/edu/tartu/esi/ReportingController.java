package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/analytics/{parkingSlotId}")
    public ResponseEntity<AnalyticsDTO> getAnalyticsByParkingSlotId(@PathVariable String parkingSlotId) {
        try {
            AnalyticsDTO analytics = reportingService.getAnalyticsByParkingSlotId(parkingSlotId);
            return ResponseEntity.ok(analytics);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/analytics")
    public ResponseEntity<List<AnalyticsDTO>> getAnalyticsByParkingSlotIds(@RequestParam("parkingSlotIds") List<String> parkingSlotIds) {
        try {
            List<AnalyticsDTO> analyticsList = reportingService.getAnalyticsByParkingSlotIds(parkingSlotIds);
            return ResponseEntity.ok(analyticsList);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
