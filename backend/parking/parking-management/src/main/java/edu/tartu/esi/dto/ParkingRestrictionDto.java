package edu.tartu.esi.dto;

import edu.tartu.esi.model.CarCategory;
import edu.tartu.esi.model.ParkingRestriction;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRestrictionDto {

    @Id
    private String id;
    private String parkingSlotId;
    private LocalDateTime from;
    private LocalDateTime until;
    private CarCategory category;
    private String code;
}
