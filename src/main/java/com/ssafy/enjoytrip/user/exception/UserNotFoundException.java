package com.ssafy.enjoytrip.user.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
