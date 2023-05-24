package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dao.QnaBoardDao;
import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BoardCommentServiceImpl implements BoardCommentService {

    private final QnaBoardDao qnaBoardDao;

    public BoardCommentServiceImpl(QnaBoardDao qnaBoardDao) {
        this.qnaBoardDao = qnaBoardDao;
    }

    @Override
    public List<BoardComment> getCommentList(int id) {
        return qnaBoardDao.selectCommentList(id);
    }

    @Override
    public int write(BoardComment boardComment) {
        return qnaBoardDao.insertComment(boardComment);
    }

    @Override
    public int modify(BoardComment boardComment) {
        return qnaBoardDao.updateComment(boardComment);
    }

    public boolean remove(BoardComment boardComment) {
        return qnaBoardDao.deleteComment(boardComment) == 1;
    }

}
