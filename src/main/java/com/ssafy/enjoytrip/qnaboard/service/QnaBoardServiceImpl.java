package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dao.QnaBoardDao;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoard;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import com.ssafy.enjoytrip.qnaboard.exception.QnaBoardNotFoundException;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QnaBoardServiceImpl implements QnaBoardService{

    private final QnaBoardDao qnaBoardDao;

    public QnaBoardServiceImpl(QnaBoardDao qnaBoardDao) {
        this.qnaBoardDao = qnaBoardDao;
    }

    @Override
    public List<QnaBoardView> getBoardListWithSearch(HashMap<String, String> params) {
        return qnaBoardDao.selectListByCondition(params);
    }

    @Override
    public int write(QnaBoardView board) {
        return qnaBoardDao.insertBoard(board);
    }

    @Override
    public int getLastId() {
        return qnaBoardDao.selectLastBoard();
    }

    //    @Override
//    public QnaBoardView getBoard(int id) {
//        this.updateHit(id);
//        return qnaBoardDao.selectOneById(id);
//    }
    @Override
    public List<QnaBoardView> getBoardList() {
        return qnaBoardDao.selectList();
    }

    @Override
    public boolean modify(QnaBoardView board) {
//        QnaBoard originBoard = qnaBoardDao.selectOneById(board.getId());
        QnaBoardView originBoardView = qnaBoardDao.selectDetailById(board.getId());
        originBoardView.setTitle(board.getTitle());
        originBoardView.setContent(board.getContent());
        return qnaBoardDao.updateBoard(originBoardView) == 1;
    }

    @Override
    public boolean remove(int id) {
        return qnaBoardDao.deleteBoard(id) == 1;
    }

    @Override
    public void updateHit(int id) {
        QnaBoard board = qnaBoardDao.selectOneById(id);
        board.setHit(board.getHit()+1);
        qnaBoardDao.updateHit(id);
    }

    @Override
    public QnaBoardView readBoardView(int id) {
        this.updateHit(id);
        if (qnaBoardDao.selectDetailById(id) == null) throw new QnaBoardNotFoundException(id+"번 게시글이 존재하지 않습니다");
        return qnaBoardDao.selectDetailById(id);
    }

}
