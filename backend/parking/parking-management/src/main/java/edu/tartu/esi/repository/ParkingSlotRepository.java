package edu.tartu.esi.repository;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSlotRepository extends CrudRepository <ParkingSlot, String> {
    @Query("SELECT e FROM ParkingSlot e WHERE e.status = :status")
    List<ParkingSlotDto> findAllByStatus(SlotStatusEnum status);
}