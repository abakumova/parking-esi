package edu.tartu.esi.controller;

import edu.tartu.esi.dto.AvailableParkingSlotDto;
import edu.tartu.esi.model.Location;
import edu.tartu.esi.service.AvailableParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class ParkingAvailabilityController {

    @Autowired
    private AvailableParkingSlotService availableParkingSlotService;

    @GetMapping("/available-slots")
    public List<AvailableParkingSlotDto> fetchAllSlots(@Valid Location location) {
        return availableParkingSlotService.searchSlots(location);
    }
}
