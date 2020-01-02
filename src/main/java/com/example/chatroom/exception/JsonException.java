package com.example.chatroom.exception;

/**
 * Json 解析异常
 */
public class JsonException extends InternalException {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    public JsonException(String msg) {
        super(msg);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }

    public JsonException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
