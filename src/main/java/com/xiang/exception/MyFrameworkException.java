package com.xiang.exception;

public class MyFrameworkException extends RuntimeException {
    public MyFrameworkException(String message) {
        super(message);
    }

    public MyFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
