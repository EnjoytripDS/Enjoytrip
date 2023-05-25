package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import java.util.List;

public interface BoardCommentService {

    List<BoardComment> getCommentList(int id);

    int write(BoardComment boardComment);

    int modify(BoardComment boardComment);

    boolean remove(BoardComment boardComment);

    int removeAllComments(int id);
}
