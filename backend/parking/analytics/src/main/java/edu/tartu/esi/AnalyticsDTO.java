package edu.tartu.esi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticsDTO {
    private String analyticsId;
    private String parkingSlotId;
    private double occupancy;
    private double revenue;
    private double avgBookingDuration;
}
