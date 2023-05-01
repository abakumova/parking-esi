package edu.tartu.esi.controller;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.service.ParkingSlotService;
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
public class ParkingSlotController {
    @Autowired
    private ParkingSlotService parkingSlotService;

    @GetMapping(value = "/parking-slots/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ParkingSlotDto getParkingSlot(@Valid @PathVariable String id) {
        return parkingSlotService.getParkingSlotById(id);
    }

    @PostMapping(value = "/parking-slots", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createParkingSlot(@Valid @RequestBody ParkingSlotDto parkingSlotDto) {
        parkingSlotService.createParkingSlot(parkingSlotDto);
        return ResponseEntity.ok("Parking slot has been created");
    }

    @PutMapping(value = "/parking-slots", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateParkingSlot(@Valid @PathVariable String id, @Valid @RequestBody ParkingSlotDto parkingSlotDto) {
        parkingSlotService.updateParkingSlot(id, parkingSlotDto);
        return ResponseEntity.ok("Parking slot has been updated");
    }

    @DeleteMapping(value = "/parking-slots/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteParkingStatus(@Valid @PathVariable String id) {
        parkingSlotService.deleteParkingSlot(id);
        return ResponseEntity.ok("Parking slot has been deleted");
    }
}
