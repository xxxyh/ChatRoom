package com.example.chatroom.model;

/**
 * 返回数据的json
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(0, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, null, data);
    }

    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(0, message, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(-1, null, null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(-1, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}