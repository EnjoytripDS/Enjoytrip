package com.ssafy.enjoytrip.qnaboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardComment {

    private int id;
    private String content;
    private String createtime;
    private String nickname;
    private int qnaBoardId;

    public BoardComment(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
    }
}
