package com.ssafy.enjoytrip.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripAttraction {
    private int id;
    private int contentId; // 관광지 정보 id
    private int dayByAttraction; // 선택한 관광지별 몇일차
    private int orderByDay; // 순서
    private int tripId;

    public TripAttraction(int contentId, int dayByAttraction, int orderByDay, int tripId) {
        this.contentId = contentId;
        this.dayByAttraction = dayByAttraction;
        this.orderByDay = orderByDay;
        this.tripId = tripId;
    }
}
