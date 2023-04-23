package edu.tartu.esi.repository;

import edu.tartu.esi.model.ParkingSlot;
import org.springframework.data.repository.CrudRepository;

public interface ParkingSlotRepository extends CrudRepository <ParkingSlot, String> {
}
