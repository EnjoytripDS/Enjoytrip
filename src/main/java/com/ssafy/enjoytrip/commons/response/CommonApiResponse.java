package com.ssafy.enjoytrip.commons.response;

import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public final class CommonApiResponse<T> {

    private final boolean success;
    private final T data;
    private final Error error;

    public CommonApiResponse(boolean success, T data, Error error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }


    public static <T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(true, data, null);
    }

    public static <T> CommonApiResponse<T> fail(ErrorCode errorCode) {
        return new CommonApiResponse<T>(
                false, null, new Error(errorCode.name(), errorCode.getMessage())
        );
    }

    public static <T> CommonApiResponse<T> fail(String code, String message) {
        return new CommonApiResponse<>(false, null, new Error(code, message));
    }

    static class Error {

        //에러 타입
        private String code;
        //에러 메시지
        private String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
