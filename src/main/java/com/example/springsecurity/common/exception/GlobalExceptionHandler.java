package com.example.springsecurity.common.exception;

import com.example.springsecurity.common.api.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if(e.getErrorCode() != null) {
            return new CommonResult(e.getErrorCode(), e.getErrorMessage());
        }

        return CommonResult.failed();
    }

}
