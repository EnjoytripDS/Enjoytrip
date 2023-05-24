package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dao.BoardCommentDao;
import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BoardCommentServiceImpl implements BoardCommentService {

    private final BoardCommentDao boardCommentDao;

    public BoardCommentServiceImpl(BoardCommentDao boardCommentDao) {
        this.boardCommentDao = boardCommentDao;
    }

    @Override
    public List<BoardComment> getCommentList(int id) {
        return boardCommentDao.selectCommentList(id);
    }

    @Override
    public int write(BoardComment boardComment) {
        return boardCommentDao.insertComment(boardComment);
    }
}
