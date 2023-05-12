package edu.tartu.esi.repository;

import edu.tartu.esi.dto.ParkingSlotDto;
import edu.tartu.esi.model.ParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSlotRepository extends CrudRepository <ParkingSlot, String> {
    @Query("SELECT e FROM ParkingSlot e WHERE e.parkingSlotStatus = :status")
    List<ParkingSlot> findAllByStatus(SlotStatusEnum status);

    @Query("SELECT e FROM ParkingSlot e WHERE e.landlordId = :landlordId")
    List<ParkingSlot> findAllByLandlordId(String landlordId);
}