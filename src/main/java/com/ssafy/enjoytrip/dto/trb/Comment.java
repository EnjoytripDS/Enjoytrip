package com.ssafy.enjoytrip.dto.trb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
