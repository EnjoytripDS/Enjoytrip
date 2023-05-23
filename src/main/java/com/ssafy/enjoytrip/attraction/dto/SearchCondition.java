package com.ssafy.enjoytrip.attraction.dto;

import java.util.List;
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
    private List<Integer> contentTypeIdList;
    private String keyword;

}
