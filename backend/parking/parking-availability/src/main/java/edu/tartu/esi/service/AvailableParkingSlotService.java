package edu.tartu.esi.service;

import edu.tartu.esi.dto.AvailableParkingSlotDto;
import edu.tartu.esi.mapper.AvailableParkingSlotMapper;
import edu.tartu.esi.model.AvailableParkingSlot;
import edu.tartu.esi.model.Location;
import edu.tartu.esi.repository.AvailableParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailableParkingSlotService {

    @Autowired
    AvailableParkingSlotRepository availableParkingSlotRepository;

    private final AvailableParkingSlotMapper availableParkingSlotMapper;

    public List<AvailableParkingSlotDto> searchSlots(Location location) {
        List<AvailableParkingSlot> parkingSlotDtoList = availableParkingSlotRepository.findAll();

        Optional<AvailableParkingSlot> result = parkingSlotDtoList
                .stream()
                .filter(slot -> distance(slot.getLocation().getLatitude(), slot.getLocation().getLongitude(),
                        location.getLatitude(), location.getLongitude()) < 1.0)
                .findAny();

        Optional<AvailableParkingSlotDto> dtoResult = result.map(availableParkingSlotMapper::toDto);
        return dtoResult
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}