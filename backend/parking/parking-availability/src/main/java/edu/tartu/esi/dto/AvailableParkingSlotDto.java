package edu.tartu.esi.dto;

import edu.tartu.esi.model.CarCategoryEnum;
import edu.tartu.esi.model.Location;
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
    private Boolean managementStatus;
    private Boolean bookingStatus;
    private CarCategoryEnum category;
    private Location location;
    private String price;
}
