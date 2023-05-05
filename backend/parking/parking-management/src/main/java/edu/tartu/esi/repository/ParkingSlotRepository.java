package edu.tartu.esi.repository;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSlotRepository extends CrudRepository <ParkingSlot, String> {
    List<ParkingSlotDto> findAllByStatus(String status);
}
