package com.ssafy.enjoytrip.user.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class UserDuplicatedEmailException extends BaseException {

    public UserDuplicatedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
