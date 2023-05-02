package com.ssafy.enjoytrip.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetGugunRequest {

    private int sidoCode;

    public int GetGugun() {
        return sidoCode;
    }
}
