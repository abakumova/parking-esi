package edu.tartu.esi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SlotStatusEnum {
    OPEN("Open", 1), //Parking management
    CLOSED("Closed", 2), //Parking management
    AVAILABLE("Available", 3), //Booking
    BOOKED("Booked", 4); //Booking


    private String parkingStatusName;
    private int parkingStatusId;

    public static SlotStatusEnum getParkingSlotStatusByName(String parkingSlotStatusName) {
        if (parkingSlotStatusName.equals(OPEN.getParkingStatusName())) {
            return OPEN;
        } else if (parkingSlotStatusName.equals(CLOSED.getParkingStatusName())) {
            return CLOSED;
        } else if (parkingSlotStatusName.equals(AVAILABLE.getParkingStatusName())) {
            return AVAILABLE;
        } else {
            return BOOKED;
        }
    }

    public static SlotStatusEnum getRoleById(int parkingStatusId) {
        return switch (parkingStatusId) {
            case 1 -> OPEN;
            case 2 -> CLOSED;
            case 3 -> AVAILABLE;
            case 4 -> BOOKED;
            default -> throw new IllegalStateException("Unexpected value: " + parkingStatusId);
        };
    }
}
