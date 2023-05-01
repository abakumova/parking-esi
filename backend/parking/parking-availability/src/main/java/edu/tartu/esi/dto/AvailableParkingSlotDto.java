package edu.tartu.esi.dto;

import edu.tartu.esi.model.CarCategoryEnum;
import edu.tartu.esi.model.Location;
import edu.tartu.esi.model.SlotStatusEnum;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableParkingSlotDto {

    @Id
    private String id;
    private String slotId;
    private SlotStatusEnum managementStatus;
    private SlotStatusEnum bookingStatus;
    private CarCategoryEnum category;
    private Location location;
    private String price;
}
