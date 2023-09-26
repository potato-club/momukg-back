package com.momukgback.error.exception;

import com.momukgback.error.ErrorCode;

public class InvalidTokenException extends  BusinessException{
    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
