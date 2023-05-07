package edu.tartu.esi.repository;

import edu.tartu.esi.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository <Booking, String> {

    List<Booking> findAllByCustomerId(String customerId);
    Optional<Booking> findBookingById(String id);
}
