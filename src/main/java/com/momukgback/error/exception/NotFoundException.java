package com.momukgback.error.exception;

import com.momukgback.error.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
