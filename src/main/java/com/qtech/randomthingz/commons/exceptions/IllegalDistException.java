package com.qtech.randomthingz.commons.exceptions;

public class IllegalDistException extends IllegalArgumentException {
    public IllegalDistException() {
        super();
    }

    public IllegalDistException(String message) {
        super(message);
    }

    public IllegalDistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDistException(Throwable cause) {
        super(cause);
    }
}
