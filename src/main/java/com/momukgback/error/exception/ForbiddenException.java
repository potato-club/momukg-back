package com.momukgback.error.exception;

import com.momukgback.error.ErrorCode;

public class ForbiddenException extends BusinessException{
    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
