package edu.tartu.esi.repository;

import edu.tartu.esi.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository <Booking, String> {
}
