package edu.tartu.esi.exception;

public class ParkingSlotException extends RuntimeException {

    public ParkingSlotException(String message) {
        super(message);
    }

    public ParkingSlotException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingSlotException(Throwable cause) {
        super(cause);
    }

    public ParkingSlotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
