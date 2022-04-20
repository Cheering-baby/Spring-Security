package com.example.springsecurity.common.exception;


import com.example.springsecurity.common.api.IErrorCode;

/**
 * 自定义API异常
 * Created by Lu Fei on 2022/04/20.
 */
public class ApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public ApiException(IErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}