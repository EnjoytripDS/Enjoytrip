package com.ssafy.enjoytrip.travelreview.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
