package com.ssafy.enjoytrip.user.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class UserDuplicatedNicknameException extends BaseException {

    public UserDuplicatedNicknameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
