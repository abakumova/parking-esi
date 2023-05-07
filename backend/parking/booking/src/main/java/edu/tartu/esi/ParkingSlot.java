package edu.tartu.esi;

import edu.tartu.esi.model.SlotStatusEnum;
import lombok.Data;

@Data
public class ParkingSlot {

    private String id;
    private String landlordId;
    private SlotStatusEnum parkingSlotStatus;
    private String price;
    private Location location;
}
