package com.qtech.forgemod.commons.exceptions;

public class ValueExistsException extends Throwable {
    public ValueExistsException() {
        super();
    }

    public ValueExistsException(String message) {
        super(message);
    }

    public ValueExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueExistsException(Throwable cause) {
        super(cause);
    }

    public ValueExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
