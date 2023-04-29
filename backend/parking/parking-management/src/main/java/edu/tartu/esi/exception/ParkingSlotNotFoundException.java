package edu.tartu.esi.exception;

public class ParkingSlotNotFoundException extends ParkingSlotException {

    public ParkingSlotNotFoundException(String message) {
        super(message);
    }

    public ParkingSlotNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingSlotNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParkingSlotNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}