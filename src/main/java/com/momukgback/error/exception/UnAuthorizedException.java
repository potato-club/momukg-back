package com.momukgback.error.exception;

import com.momukgback.error.ErrorCode;

public class UnAuthorizedException extends BusinessException{
    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
