package edu.tartu.esi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends CrudRepository<Analytics, String> {
    Analytics findByParkingSlotId(String parkingSlotId);
}
