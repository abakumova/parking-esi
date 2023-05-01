package edu.tartu.esi.exception;

public class NotEnoughMoneyToPerformOperationException extends RuntimeException {

    public NotEnoughMoneyToPerformOperationException(String message) {
        super(message);
    }

    public NotEnoughMoneyToPerformOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoneyToPerformOperationException(Throwable cause) {
        super(cause);
    }

    public NotEnoughMoneyToPerformOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
