package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dao.QnaBoardDao;
import com.ssafy.enjoytrip.qnaboard.dto.BoardImage;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BoardImageServiceImpl implements BoardImageService {

    private final QnaBoardDao qnaBoardDao;

    public BoardImageServiceImpl(QnaBoardDao qnaBoardDao) {
        this.qnaBoardDao = qnaBoardDao;
    }
    @Override
    public int uploadImage(BoardImage boardImage) {
        return qnaBoardDao.saveImage(boardImage);
    }

    @Override
    public List<BoardImage> getImage(int id) {
        return qnaBoardDao.selectImage(id);
    }
}
