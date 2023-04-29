package edu.tartu.esi.repository;

import edu.tartu.esi.model.ParkingRestriction;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRestrictionRepository extends CrudRepository <ParkingRestriction, String> {
}
