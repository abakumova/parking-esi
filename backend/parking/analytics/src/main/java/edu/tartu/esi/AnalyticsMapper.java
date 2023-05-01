package edu.tartu.esi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

    @Mappings({
            @Mapping(source = "analytics.analyticsId", target = "analyticsId"),
            @Mapping(source = "analytics.parkingSlotId", target = "parkingSlotId"),
            @Mapping(source = "analytics.occupancy", target = "occupancy"),
            @Mapping(source = "analytics.revenue", target = "revenue"),
            @Mapping(source = "analytics.totalBookingDuration", target = "avgBookingDuration")
    })
    AnalyticsDTO toDto(Analytics analytics);
}
