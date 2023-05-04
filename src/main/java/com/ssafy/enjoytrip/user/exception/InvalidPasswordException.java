package com.ssafy.enjoytrip.user.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("비밀번호가 맞지 않습니다.");
    }
}
