package com.ssafy.enjoytrip.qnaboard.dao;

import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardCommentDao {
    List<BoardComment> selectCommentList(int id);

    int insertComment(BoardComment boardComment);
}
