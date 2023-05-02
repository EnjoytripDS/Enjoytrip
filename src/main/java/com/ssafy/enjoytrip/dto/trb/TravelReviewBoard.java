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
public class TravelReviewBoard {

    private int id;
    private String title;
    private String content;
    private int hit;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
    private int userId;
}
