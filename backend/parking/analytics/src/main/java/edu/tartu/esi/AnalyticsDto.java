package edu.tartu.esi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticsDto {


    private String analyticsId;

    private String id;
    private String parkingSlotId;
    private double occupancy;
    private double revenue;
}
