package com.ssafy.enjoytrip.qnaboard.exception;

import com.ssafy.enjoytrip.commons.exception.BaseException;
import com.ssafy.enjoytrip.commons.exception.ErrorCode;

public class ImageNotFoundException extends BaseException {

    public ImageNotFoundException(String message) {
        super(message, ErrorCode.IMAGE_NOT_FOUND);
    }
}
