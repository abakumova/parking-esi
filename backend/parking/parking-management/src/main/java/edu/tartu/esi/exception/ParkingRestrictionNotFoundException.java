package edu.tartu.esi.exception;

public class ParkingRestrictionNotFoundException extends ParkingSlotException {

    public ParkingRestrictionNotFoundException(String message) {
        super(message);
    }

    public ParkingRestrictionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingRestrictionNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParkingRestrictionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}