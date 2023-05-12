package edu.tartu.esi;

import edu.tartu.esi.security.JwtRole;
import edu.tartu.esi.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static edu.tartu.esi.security.Role.ADMIN;
import static edu.tartu.esi.security.Role.LANDLORD;

@RestController
@RequestMapping("/api/v1/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/{parkingSlotId}")
    @JwtRole(roles = {ADMIN, LANDLORD})
    public ResponseEntity<AnalyticsDto> getAnalytics(@PathVariable String parkingSlotId) {
        AnalyticsDto analyticsDTO = analyticsService.getAnalytics(parkingSlotId);
        if (analyticsDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(analyticsDTO);
    }
}
