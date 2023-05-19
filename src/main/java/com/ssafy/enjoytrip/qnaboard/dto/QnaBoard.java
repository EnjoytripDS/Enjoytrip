package com.ssafy.enjoytrip.qnaboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QnaBoard {

    private int id;
    private String title;
    private String content;
    private String createtime;
    private String updatetime;
    private int hit;
    private int userId;

    public QnaBoard(String title, String content, int userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
