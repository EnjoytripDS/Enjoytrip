package com.ssafy.enjoytrip.commons.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

    // User Error
    UN_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),
    UNAUTHORIZED_ROLE(HttpStatus.UNAUTHORIZED, "현재 유저 권한으로는 접근할 수 없는 리소스 요청입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일 입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    QNA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 QNA 게시물이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
