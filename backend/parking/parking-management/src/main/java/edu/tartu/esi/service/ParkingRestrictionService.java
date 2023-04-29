package edu.tartu.esi.service;

import edu.tartu.esi.dto.ParkingRestrictionDto;
import edu.tartu.esi.exception.ParkingRestrictionNotFoundException;
import edu.tartu.esi.mapper.ParkingRestrictionMapper;
import edu.tartu.esi.model.ParkingRestriction;
import edu.tartu.esi.model.ParkingRestriction;
import edu.tartu.esi.repository.ParkingRestrictionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingRestrictionService {

    @Autowired
    private ParkingRestrictionRepository parkingRestrictionRepository;
    private final ParkingRestrictionMapper parkingRestrictionMapper;

    public void createParkingRestriction(ParkingRestrictionDto parkingRestrictionDto) {
        assertParkingRestrictionDto(parkingRestrictionDto, "Can't create a parking slot info when parking slot is null");
        ParkingRestriction parkingRestriction = ParkingRestriction.builder()
                .id(parkingRestrictionDto.getId())
                .parkingSlotId(parkingRestrictionDto.getParkingSlotId())
                .from(parkingRestrictionDto.getFrom())
                .until(parkingRestrictionDto.getUntil())
                .category(parkingRestrictionDto.getCategory())
                .code(parkingRestrictionDto.getCode())
                .build();
        parkingRestrictionRepository.save(parkingRestriction);
        log.info("Parking restriction {} is added to the Database", parkingRestriction.getId());
    }

    @Transactional
    public ParkingRestrictionDto getParkingRestrictionById(String id) {
        log.info("-- fetch parking restrictions");
        ParkingRestriction parkingRestriction = parkingRestrictionRepository.findById(id)
                .orElseThrow(() -> new ParkingRestrictionNotFoundException(format("Parking restriction with id = %s wan't found", id)));
        return parkingRestrictionMapper.toDto(parkingRestriction);
    }

    public void updateParkingRestriction(String id, ParkingRestrictionDto parkingRestrictionDto) {
        ParkingRestriction parkingRestriction = ParkingRestriction.builder()
                .id(id)
                .parkingSlotId(parkingRestrictionDto.getParkingSlotId())
                .from(parkingRestrictionDto.getFrom())
                .until(parkingRestrictionDto.getUntil())
                .category(parkingRestrictionDto.getCategory())
                .code(parkingRestrictionDto.getCode())
                .build();
        parkingRestrictionRepository.save(parkingRestriction);
        log.info("-- Parking restriction {} has been updated", parkingRestriction.getId());
    }

    public void deleteParkingRestriction(String id) {
        parkingRestrictionRepository.deleteById(id);
        log.info("-- Parking restriction {} has been deleted", id);
    }

    private void assertParkingRestrictionDto(ParkingRestrictionDto parking, String msg) {
        if (parking == null){
            log.info("The body is missing");
            throw new IllegalArgumentException(msg);
        }
    }
}
