package edu.tartu.esi.controller;

import edu.tartu.esi.dto.PaginatedResponseDto;
import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.search.GenericSearchDto;
import edu.tartu.esi.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @GetMapping(value = "/parking-slots/by-id/{slotId}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ParkingSlotDto getParkingSlot(@Valid @PathVariable String slotId) {
        return parkingSlotService.getParkingSlotById(slotId);
    }

    @GetMapping(value = "/parking-slots/by-status/{status}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSlot> getParkingSlotByStatus(@Valid @PathVariable SlotStatusEnum status) {
        return parkingSlotService.getParkingSlotByStatus(status);
    }

    @GetMapping(value = "/parking-slots/by-location/{lat}/{lon}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSlot> getParkingSlotByLocation(@Valid @PathVariable String lat, @Valid @PathVariable String lon,
                                                      @RequestParam(required = false) String distance) {
        Optional<String> distanceOpt = Optional.ofNullable(distance);
        return parkingSlotService.getParkingSlotByLocation(lat, lon, distanceOpt);
    }

    @GetMapping(value = "/parking-slots/by-landlord/{landlordId}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSlot> getParkingSlotByLandlord(@Valid @PathVariable String landlordId) {
        return parkingSlotService.getParkingSlotByLandlord(landlordId);
    }

    @GetMapping(value = "/parking-slots", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public PaginatedResponseDto<ParkingSlotDto> getParkingSlots(GenericSearchDto<ParkingSlotDto> genericSearchDto) {
        return parkingSlotService.getParkingSlots(genericSearchDto);
    }

    @PostMapping(value = "/parking-slots", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createParkingSlot(@Valid @RequestBody ParkingSlotDto parkingSlotDto) {
        parkingSlotService.createParkingSlot(parkingSlotDto);
        return ResponseEntity.ok("Parking slot has been created");
    }

    @PutMapping(value = "/parking-slots/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateParkingSlot(@Valid @PathVariable String id, @Valid @RequestBody ParkingSlotDto parkingSlotDto) {
        parkingSlotService.updateParkingSlot(id, parkingSlotDto);
        return ResponseEntity.ok("Parking slot has been updated");
    }

    @PutMapping(value = "/parking-slots/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateParkingSlotStatus(@Valid @PathVariable String id, @Valid @RequestBody SlotStatusEnum status) {
        parkingSlotService.updateParkingSlotStatus(id, status);
        return ResponseEntity.ok("Parking slot status has been updated");
    }

    @DeleteMapping(value = "/parking-slots/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteParkingStatus(@Valid @PathVariable String id) {
        parkingSlotService.deleteParkingSlot(id);
        return ResponseEntity.ok("Parking slot has been deleted");
    }
}
