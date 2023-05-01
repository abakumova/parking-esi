package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/{parkingSlotId}")
    public ResponseEntity<AnalyticsDTO> getAnalytics(@PathVariable String parkingSlotId) {
        AnalyticsDTO analyticsDTO = analyticsService.getAnalytics(parkingSlotId);
        if (analyticsDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(analyticsDTO);
    }
}
