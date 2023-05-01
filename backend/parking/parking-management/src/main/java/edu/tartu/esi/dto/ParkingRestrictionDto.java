package edu.tartu.esi.dto;

import edu.tartu.esi.model.CarCategoryEnum;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRestrictionDto {

    @Id
    private String id;
    private String parkingSlotId;
    private LocalDateTime timeFrom;
    private LocalDateTime timeUntil;
    private CarCategoryEnum category;
    private String code;
}
