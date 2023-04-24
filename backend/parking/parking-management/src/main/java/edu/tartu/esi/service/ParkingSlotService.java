package edu.tartu.esi.service;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.repository.ParkingSlotRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    private ParkingSlotDto mapToParkingSlotDto(ParkingSlot parkingSlot) {
        return ParkingSlotDto.builder()
                .id(parkingSlot.getId())
                .landlordId(parkingSlot.getLandlordId())
                .price(parkingSlot.getPrice())
                .parkingRestrictions(parkingSlot.getParkingRestrictions())
                .build();
    }

    public Optional<ParkingSlotDto> getParkingSlot(String id) {
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(id);
        return parkingSlot.map(this::mapToParkingSlotDto);
    }

    public void addParkingSlot(ParkingSlotDto parkingSlotDto) {
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(parkingSlotDto.getId())
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .parkingRestrictions(parkingSlotDto.getParkingRestrictions())
                .build();
        parkingSlotRepository.save(parkingSlot);
        log.info("Parking Slot {} is added to the Database", parkingSlot.getId());
    }

    public void updateParkingSlot(ParkingSlotDto parkingSlotDto) {
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(parkingSlotDto.getId())
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .parkingRestrictions(parkingSlotDto.getParkingRestrictions())
                .build();
        parkingSlotRepository.save(parkingSlot);
        log.info("Parking Slot {} is added to the Database", parkingSlot.getId());
    }

    public void deleteParkingSlot(String id) {
        parkingSlotRepository.deleteById(id);
        log.info("Parking slot has been deleted");
    }
}
