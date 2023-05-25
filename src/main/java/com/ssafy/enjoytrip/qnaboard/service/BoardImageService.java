package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dto.BoardImage;
import java.util.List;

public interface BoardImageService {

    int uploadImage(BoardImage boardImage);

    List<BoardImage> getImage(int id);
}
