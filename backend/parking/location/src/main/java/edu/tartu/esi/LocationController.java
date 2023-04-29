package edu.tartu.esi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<String> processAddress(@RequestParam String address) {
        try {
            locationService.processAddress(address);
            return new ResponseEntity<>("Address processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing address: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
