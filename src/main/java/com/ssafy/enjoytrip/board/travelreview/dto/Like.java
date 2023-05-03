package com.ssafy.enjoytrip.board.travelreview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Like {

    private int id;
    private int trbId;
    private int userId;
}
