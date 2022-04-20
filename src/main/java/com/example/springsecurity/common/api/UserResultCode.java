package com.example.springsecurity.common.api;

public enum UserResultCode implements IErrorCode {
    USERNAME_REQUIRED(001, "USERNAME REQUIRED"),
    PASSWORD_REQUIRED(002, "PASSWORD REQUIRED"),
    STATUS_LOCKED(003, "STATUS LOCKED")
    ;
    private long code;
    private String message;

    private static final String MODEL_NAME = "APP-UMS-";

    UserResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return MODEL_NAME + this.code;
    }

    @Override
    public String getMessage() {
        return  this.message;
    }
}
