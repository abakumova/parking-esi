package edu.tartu.esi.service;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.exception.ParkingSlotNotFoundException;
import edu.tartu.esi.kafka.KafkaConsumerService;
import edu.tartu.esi.mapper.ParkingSlotMapper;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.repository.ParkingSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;
    @Autowired
    KafkaConsumerService kafkaConsumerService;
    private final ParkingSlotMapper parkingSlotMapper;

    public void createParkingSlot(ParkingSlotDto parkingSlotDto) {
        assertParkingSlotDto(parkingSlotDto, "Can't create a parking slot info when parking slot is null");
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(parkingSlotDto.getId())
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .parkingSlotStatus(SlotStatusEnum.OPEN)
                .parkingRestrictions(parkingSlotDto.getParkingRestrictions())
                .location(parkingSlotDto.getLocation())
                .build();
        parkingSlotRepository.save(parkingSlot);
        kafkaConsumerService.sendManagementStatusCreated(parkingSlot);
        log.info("Parking Slot {} is added to the Database", parkingSlot.getId());
    }

    @Transactional
    public ParkingSlotDto getParkingSlotById(String id) {
        log.info("-- fetch parking slots");
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ParkingSlotNotFoundException(format("Parking slot with id = %s wan't found", id)));
        return parkingSlotMapper.toDto(parkingSlot);
    }

    public void updateParkingSlot(String id, ParkingSlotDto parkingSlotDto) {
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(id)
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .parkingRestrictions(parkingSlotDto.getParkingRestrictions())
                .location(parkingSlotDto.getLocation())
                .build();
        parkingSlotRepository.save(parkingSlot);
        kafkaConsumerService.sendManagementStatusUpdated(parkingSlot);
        log.info("-- Parking Slot {} has been updated", parkingSlot.getId());
    }

    public void deleteParkingSlot(String id) {
        parkingSlotRepository.deleteById(id);
        kafkaConsumerService.sendManagementStatusDeleted(id);
        log.info("-- Parking slot {} has been deleted", id);
    }


    public List<ParkingSlotDto> getParkingSlotByStatus(String status) {
        return parkingSlotRepository.findAllByStatus(status);
    }

    private void assertParkingSlotDto(ParkingSlotDto parking, String msg) {
        if (parking == null) {
            log.info("The body is missing");
            throw new IllegalArgumentException(msg);
        }
    }
}
