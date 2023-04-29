package edu.tartu.esi.controller;

import edu.tartu.esi.dto.ParkingRestrictionDto;
import edu.tartu.esi.service.ParkingRestrictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1")
public class ParkingRestrictionController {
    @Autowired
    private ParkingRestrictionService parkingRestrictionService;

    @GetMapping(value = "/parking-restrictions/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ParkingRestrictionDto getParkingRestriction(@Valid @PathVariable String id) {
        return parkingRestrictionService.getParkingRestrictionById(id);
    }

    @PostMapping(value = "/parking-restrictions", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createParkingRestriction(@Valid @RequestBody ParkingRestrictionDto parkingRestrictionDto) {
        parkingRestrictionService.createParkingRestriction(parkingRestrictionDto);
        return ResponseEntity.ok("Parking restriction has been created");
    }

    @PutMapping(value = "/parking-restrictions", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateParkingRestriction(@Valid @PathVariable String id, @Valid @RequestBody ParkingRestrictionDto parkingRestrictionDto) {
        parkingRestrictionService.updateParkingRestriction(id, parkingRestrictionDto);
        return ResponseEntity.ok("Parking restriction has been updated");
    }

    @DeleteMapping(value = "/parking-restrictions/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteParkingStatus(@Valid @PathVariable String id) {
        parkingRestrictionService.deleteParkingRestriction(id);
        return ResponseEntity.ok("Parking restriction has been deleted");
    }
}
