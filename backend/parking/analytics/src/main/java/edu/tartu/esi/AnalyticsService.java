package edu.tartu.esi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Slf4j
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Autowired
    private AnalyticsMapper analyticsMapper;


    @Transactional
    @KafkaListener(topics = "booking-topic", groupId = "analytics-group")
    public void onBookingCreated(BookingDto bookingDto) {
        String parkingSlotId = bookingDto.getParkingSlotId();
        Analytics analytics = analyticsRepository.findByParkingSlotId(parkingSlotId);
        if (analytics == null) {
            analytics = Analytics.builder()
                    .parkingSlotId(parkingSlotId)
                    .build();
        }

        double durationInHours = Duration.between(bookingDto.getTimeFrom(), bookingDto.getTimeUntil()).toMinutes() / 60.0;
        double revenue = Double.parseDouble(bookingDto.getPrice()) * durationInHours;
        long totalBookingCount = analytics.getTotalBookingCount() + 1;
        long totalBookingDuration = analytics.getTotalBookingDuration() + (long) (durationInHours * 60);

        analytics.setOccupancy(totalBookingCount == 0 ? 0 : (double) totalBookingDuration / (totalBookingCount * 24));
        analytics.setRevenue(analytics.getRevenue() + revenue);
        analytics.setTotalBookingCount(totalBookingCount);
        analytics.setTotalBookingDuration(totalBookingDuration);

        analyticsRepository.save(analytics);
    }

    public AnalyticsDto getAnalytics(String parkingSlotId) {
        Analytics analytics = analyticsRepository.findByParkingSlotId(parkingSlotId);
        log.debug(analytics.toString());
        if (analytics == null) {
            return null;
        }

        return analyticsMapper.toDto(analytics);
    }
}
