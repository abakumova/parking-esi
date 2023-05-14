package edu.tartu.esi;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class AnalyticsDto {


    private String analyticsId;

    private String id;
    private String parkingSlotId;
    private double occupancy;
    private double revenue;
    private long totalBookingCount;
    private long totalBookingDuration;
}
