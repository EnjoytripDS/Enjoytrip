package com.ssafy.enjoytrip.user.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class InvalidPasswordException extends BaseException {

    public InvalidPasswordException(String message) {
        super(message, ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
