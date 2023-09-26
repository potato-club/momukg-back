package com.momukgback.error.exception;

import com.momukgback.error.ErrorCode;

public class BaseException extends BusinessException{
    public BaseException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
