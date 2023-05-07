package edu.tartu.esi.dto;

import edu.tartu.esi.model.Location;
import edu.tartu.esi.model.SlotStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotDto {

    @Id
    private String id;
    private String landlordId;
    private String price;
    private Location location;
    @Enumerated(EnumType.STRING)
    private SlotStatusEnum parkingSlotStatus;
}
