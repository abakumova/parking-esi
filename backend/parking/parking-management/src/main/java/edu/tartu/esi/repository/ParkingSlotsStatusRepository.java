package edu.tartu.esi.repository;

import edu.tartu.esi.model.ParkingSlotStatus;
import org.springframework.data.repository.CrudRepository;

public interface ParkingSlotsStatusRepository extends CrudRepository <ParkingSlotStatus, String> {
}
