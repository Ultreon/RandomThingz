package com.qsoftware.forgemod.common.exceptions;

public class UnidentifiedObjectException extends RuntimeException {
    public UnidentifiedObjectException() {
        super();
    }

    public UnidentifiedObjectException(String message) {
        super(message);
    }

    public UnidentifiedObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnidentifiedObjectException(Throwable cause) {
        super(cause);
    }

    public UnidentifiedObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
