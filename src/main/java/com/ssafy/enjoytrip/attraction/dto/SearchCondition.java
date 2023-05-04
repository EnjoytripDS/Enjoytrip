package com.ssafy.enjoytrip.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCondition {

    private int sidoCode;
    private int gugunCode;
    private int contentTypeId;
    private String keyword;

}
