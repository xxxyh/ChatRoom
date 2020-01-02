package com.example.chatroom.utils;

public abstract class Assert extends org.springframework.util.Assert {

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }
}
