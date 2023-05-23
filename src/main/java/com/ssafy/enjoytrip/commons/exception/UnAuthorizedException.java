package com.ssafy.enjoytrip.commons.exception;

public class UnAuthorizedException extends BaseException {


    public UnAuthorizedException() {
        super(ErrorCode.UN_AUTHENTICATED);
    }
}