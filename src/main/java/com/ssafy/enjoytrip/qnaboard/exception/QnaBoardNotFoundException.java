package com.ssafy.enjoytrip.qnaboard.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class QnaBoardNotFoundException extends BaseException {

    public QnaBoardNotFoundException(String msg) {
        super(msg, ErrorCode.QNA_NOT_FOUND);
    }

}
