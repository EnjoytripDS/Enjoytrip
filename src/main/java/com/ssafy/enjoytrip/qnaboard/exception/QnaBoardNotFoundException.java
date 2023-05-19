package com.ssafy.enjoytrip.qnaboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QnaBoardNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QnaBoardNotFoundException(String msg) {
        super(msg);
    }

}
