package com.ssafy.enjoytrip.board.travelreview.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    private int id;
    private int userId;
    private int trbId;
    private String content;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
}
