package edu.tartu.esi.exception;

public class ParkingRestrictionException extends RuntimeException {

    public ParkingRestrictionException(String message) {
        super(message);
    }

    public ParkingRestrictionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingRestrictionException(Throwable cause) {
        super(cause);
    }

    public ParkingRestrictionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
