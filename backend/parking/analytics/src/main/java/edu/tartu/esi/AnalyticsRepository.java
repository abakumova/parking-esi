package edu.tartu.esi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, String> {
    Analytics findByParkingSlotId(String parkingSlotId);
}
