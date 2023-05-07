package edu.tartu.esi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SlotStatusEnum {
    OPEN("Open", 1), //Parking management
    CLOSED("Closed", 2); //Parking management


    private String parkingStatusName;
    private int parkingStatusId;

    public static SlotStatusEnum getParkingSlotStatusByName(String parkingSlotStatusName) {
        if (parkingSlotStatusName.equals(OPEN.getParkingStatusName())) {
            return OPEN;
        } else {
            return CLOSED;
        }
    }

    public static SlotStatusEnum getParkingSlotById(int parkingStatusId) {
        return switch (parkingStatusId) {
            case 1 -> OPEN;
            case 2 -> CLOSED;
            default -> throw new IllegalStateException("Unexpected value: " + parkingStatusId);
        };
    }
}
