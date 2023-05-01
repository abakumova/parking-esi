package edu.tartu.esi.repository;

import edu.tartu.esi.model.AvailableParkingSlot;
import edu.tartu.esi.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository <Booking, String> {

    List<Booking> findAllByCustomerId(String customerId);
}
