package edu.tartu.esi.dto;

import edu.tartu.esi.model.ParkingRestriction;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotDto {

    @Id
    private String id;
    private String landlordId;
    private String price;
    private List<ParkingRestriction> parkingRestrictions;
}
