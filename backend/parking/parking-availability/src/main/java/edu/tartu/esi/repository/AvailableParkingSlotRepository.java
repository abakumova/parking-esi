package edu.tartu.esi.repository;

import edu.tartu.esi.model.AvailableParkingSlot;
import edu.tartu.esi.model.SlotStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvailableParkingSlotRepository extends CrudRepository<AvailableParkingSlot, String> {

    @Query("SELECT e FROM AvailableParkingSlot e WHERE e.managementStatus = 'OPEN' AND e.bookingStatus = 'AVAILABLE'")
    List<AvailableParkingSlot> findAll();

    AvailableParkingSlot findAvailableParkingSlotBySlotId(String id);

    @Query("UPDATE AvailableParkingSlot SET bookingStatus = :status WHERE slotId = :id")
    void updateBookingStatus(@Param("id") String id, @Param("status") SlotStatusEnum status);

    @Query("UPDATE AvailableParkingSlot SET managementStatus = :status WHERE slotId = :id")
    void updateManagementStatus(@Param("id") String id, @Param("status") SlotStatusEnum status);
}