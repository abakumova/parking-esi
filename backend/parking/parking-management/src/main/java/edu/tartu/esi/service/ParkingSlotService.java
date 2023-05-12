package edu.tartu.esi.service;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.exception.ParkingSlotNotFoundException;
import edu.tartu.esi.mapper.ParkingSlotMapper;
import edu.tartu.esi.model.Location;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import edu.tartu.esi.repository.ParkingSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;
    private final ParkingSlotMapper parkingSlotMapper;

    public void createParkingSlot(ParkingSlotDto parkingSlotDto) {
        assertParkingSlotDto(parkingSlotDto, "Can't create a parking slot info when parking slot is null");
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(parkingSlotDto.getId())
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .status(SlotStatusEnum.OPEN)
                .name(parkingSlotDto.getName())
                .location(parkingSlotDto.getLocation())
                .build();
        parkingSlotRepository.save(parkingSlot);
        log.info("Parking Slot {} is added to the Database", parkingSlot.getId());
    }

    @Transactional
    public ParkingSlotDto getParkingSlotById(String id) {
        log.info("-- fetch parking slots");
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ParkingSlotNotFoundException(format("Parking slot with id = %s wasn't found", id)));
        return parkingSlotMapper.toDto(parkingSlot);
    }

    public void updateParkingSlot(String id, ParkingSlotDto parkingSlotDto) {
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(id)
                .landlordId(parkingSlotDto.getLandlordId())
                .price(parkingSlotDto.getPrice())
                .location(parkingSlotDto.getLocation())
                .name(parkingSlotDto.getName())
                .build();
        parkingSlotRepository.save(parkingSlot);
        log.info("-- Parking Slot {} has been updated", parkingSlot.getId());
    }

    public void deleteParkingSlot(String id) {
        parkingSlotRepository.deleteById(id);
        log.info("-- Parking slot {} has been deleted", id);
    }


    public List<ParkingSlot> getParkingSlotByStatus(SlotStatusEnum status) {
        log.info("-- getParkingSlotByStatus");
        return parkingSlotRepository.findAllByStatus(status);
    }

    public List<ParkingSlot> getParkingSlotByLocation(String lat, String lon) {
        List<ParkingSlot> list = parkingSlotRepository.findAllByStatus(SlotStatusEnum.OPEN);
        log.debug("-- getParkingSlotByLocation Status OPEN {}", list);
        List<ParkingSlot> result = list
                .stream()
                .filter(slot -> distance(slot.getLocation().getLatitude(), slot.getLocation().getLongitude(),
                        Double.parseDouble(lat), Double.parseDouble(lon)) < 1.0) //1 KM
                .collect(Collectors.toList());
        log.warn("-- getParkingSlotByLocation LOCATION {}", result);

        return result;
    }

    public void updateParkingSlotStatus(String id, SlotStatusEnum status) {
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ParkingSlotNotFoundException(format("Parking slot with id = %s wasn't found", id)));
        parkingSlot.setStatus(status);
        parkingSlotRepository.save(parkingSlot);
        log.info("-- Parking Slot {} has been updated", parkingSlot.getId());
    }


    public List<ParkingSlot> getParkingSlotByLandlord(String landlordId) {
        log.debug("-- getParkingSlotByLandlord LandlordId {}", landlordId);
        return parkingSlotRepository.findAllByLandlordId(landlordId);
    }

    private void assertParkingSlotDto(ParkingSlotDto parking, String msg) {
        if (parking == null) {
            log.info("The body is missing");
            throw new IllegalArgumentException(msg);
        }
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
