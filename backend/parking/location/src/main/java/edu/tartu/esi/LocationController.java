package edu.tartu.esi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<LocationMessage> processAddress(@RequestParam String address) {
        try {
            LocationMessage locationMessage = locationService.processAddress(address);
            return ResponseEntity.ok().body(locationMessage);
        } catch (Exception e) {
            return (ResponseEntity<LocationMessage>) ResponseEntity.badRequest();
        }
    }
}
