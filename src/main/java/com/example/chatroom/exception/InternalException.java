package com.example.chatroom.exception;

public class InternalException extends RuntimeException {
    protected static final long SERIAL_VERSION_UID = 1L;
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    public InternalException(String msg) {
        super(msg);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
