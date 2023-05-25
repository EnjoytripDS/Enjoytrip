package com.ssafy.enjoytrip.qnaboard.dao;

import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import com.ssafy.enjoytrip.qnaboard.dto.BoardImage;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoard;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaBoardDao {


    List<QnaBoardView> selectListByCondition(HashMap<String, String> params);

    int insertBoard(QnaBoardView board);

    QnaBoard selectOneById(int id);

    int updateBoard(QnaBoardView originBoard);

    int deleteBoard(int id);

    void updateHit(int id);

    QnaBoardView selectDetailById(int id);

    List<QnaBoardView> selectList();

    List<BoardComment> selectCommentList(int id);

    int insertComment(BoardComment boardComment);

    int updateComment(BoardComment boardComment);

    int deleteComment(BoardComment boardComment);

    int saveImage(BoardImage boardImage);

    int selectLastBoard();

    List<BoardImage> selectImage(int id);

    int deleteAllImages(int id);

    int deleteAllComments(int id);
}
