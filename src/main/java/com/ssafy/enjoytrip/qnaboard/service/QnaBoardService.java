package com.ssafy.enjoytrip.qnaboard.service;

import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import java.util.HashMap;
import java.util.List;

public interface QnaBoardService {

    List<QnaBoardView> getBoardListWithSearch(HashMap<String, String> params);

    int write(QnaBoardView board);

//    QnaBoardView getBoard(int id);

    int getLastId();

    boolean modify(QnaBoardView board);

    boolean remove(int id);

    void updateHit(int id);

    QnaBoardView readBoardView(int id);

    List<QnaBoardView> getBoardList();
}
