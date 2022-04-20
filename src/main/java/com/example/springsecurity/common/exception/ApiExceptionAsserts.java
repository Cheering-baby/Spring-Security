package com.example.springsecurity.common.exception;

import com.example.springsecurity.common.api.IErrorCode;
import org.springframework.util.StringUtils;

public class ApiExceptionAsserts {
    public static void hasText(String text, IErrorCode errorCode) {
        if(!StringUtils.hasText(text)) {
            throw new ApiException(errorCode);
        }
    }
}
