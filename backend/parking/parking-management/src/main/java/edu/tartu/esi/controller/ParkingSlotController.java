package edu.tartu.esi.controller;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ParkingSlotController {
    @Autowired
    private ParkingSlotService parkingSlotService;

    @GetMapping("/parking-slots/{parkingSlotId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ParkingSlotDto> getParkingSlot(@PathVariable String parkingSlotId) {
        return parkingSlotService.getParkingSlot(parkingSlotId);
    }

    @PostMapping("/parking-slots")
    @ResponseStatus(HttpStatus.OK)
    public void addParkingSlot(@RequestBody ParkingSlotDto parkingSlotDto, @PathVariable String parkingSlotId) {

    }

    @PutMapping("/parking-slots/")
    @ResponseStatus(HttpStatus.OK)
    public void updateParkingSlot(@RequestBody ParkingSlotDto parkingSlotDto, @PathVariable String parkingSlotId) {

    }

    @DeleteMapping("/parking-slots/{parkingSlotId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteParkingStatus(@PathVariable String parkingSlotId) {
        parkingSlotService.deleteParkingSlot(parkingSlotId);
    }


}
