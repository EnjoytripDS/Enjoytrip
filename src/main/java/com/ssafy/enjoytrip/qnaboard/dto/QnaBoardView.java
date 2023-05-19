package com.ssafy.enjoytrip.qnaboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QnaBoardView {

    private int id;
    private String title;
    private String content;
    private String createtime;
    private int hit;
    private String nickname;

    public QnaBoardView(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;

    }
}
