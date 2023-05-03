package com.ssafy.enjoytrip.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("미등록 유저");
    }
}
