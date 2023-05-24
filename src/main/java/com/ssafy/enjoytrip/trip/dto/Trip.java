package com.ssafy.enjoytrip.trip.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Trip {
    private int id;
    private int firstdate;
    private int lastdate;
    private List<TripAttraction> tripAttractionList;
    private String createtime;
    private String updatetime;

    public Trip(int firstdate, int lastdate, List<TripAttraction> tripAttractionList) {
        this.firstdate = firstdate;
        this.lastdate = lastdate;
        this.tripAttractionList = tripAttractionList;
    }
}
