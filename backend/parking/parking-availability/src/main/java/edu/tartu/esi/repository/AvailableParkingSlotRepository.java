package edu.tartu.esi.repository;

import edu.tartu.esi.model.AvailableParkingSlot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailableParkingSlotRepository extends CrudRepository<AvailableParkingSlot, String> {

    @Query("SELECT e FROM AvailableParkingSlot e WHERE e.managementStatus = 'OPEN' AND e.bookingStatus = 'AVAILABLE'")
    List<AvailableParkingSlot> findAll();
}