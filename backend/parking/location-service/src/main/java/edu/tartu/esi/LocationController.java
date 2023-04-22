package edu.tartu.esi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/{partialAddress}")
    public ResponseEntity<String> getFullAddress(@PathVariable String partialAddress) {
        try {
            String fullAddress = locationService.getFullAddress(partialAddress);
            return ResponseEntity.ok(fullAddress);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while retrieving the full address.");
        }
    }
}
