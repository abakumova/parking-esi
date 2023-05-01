//package edu.tartu.esi.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public enum ParkingSlotStatusEnum {
//    CREATED("Created", 1),
//    AVAILABLE("Available", 2),
//    BOOKED("Booked", 3),
//    DENIED("Denied", 4),
//    CLOSED("Closed", 5);
//
//
//    private String parkingStatusName;
//    private int parkingStatusId;
//
//    public static ParkingSlotStatusEnum getParkingSlotStatusByName(String parkingSlotStatusName) {
//        if (parkingSlotStatusName.equals(CREATED.getParkingStatusName())) {
//            return CREATED;
//        } else if (parkingSlotStatusName.equals(AVAILABLE.getParkingStatusName())) {
//            return AVAILABLE;
//        } else if (parkingSlotStatusName.equals(BOOKED.getParkingStatusName())) {
//            return BOOKED;
//        } else if (parkingSlotStatusName.equals(CLOSED.getParkingStatusName())) {
//            return CLOSED;
//        } else {
//            return DENIED;
//        }
//    }
//
//    public static ParkingSlotStatusEnum getRoleById(int parkingStatusId) {
//        return switch (parkingStatusId) {
//            case 1 -> CREATED;
//            case 2 -> AVAILABLE;
//            case 3 -> BOOKED;
//            case 4 -> DENIED;
//            case 5 -> CLOSED;
//            default -> throw new IllegalStateException("Unexpected value: " + parkingStatusId);
//        };
//    }
//}
