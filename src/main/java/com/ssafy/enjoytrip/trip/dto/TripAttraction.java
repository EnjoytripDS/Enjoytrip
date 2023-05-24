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
    private int contentId;
    private int dayByAttraction;
    private int orderByDay;


    public TripAttraction(int contentId, int dayByAttraction, int orderByDay) {
        this.contentId = contentId;
        this.dayByAttraction = dayByAttraction;
        this.orderByDay = orderByDay;
    }
}
