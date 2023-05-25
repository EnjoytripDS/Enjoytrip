package com.ssafy.enjoytrip.trip.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trip {

    private int id;
    private String name;
    private String  firstdate;
    private String lastdate;
    private String createtime;
    private String updatetime;


    public Trip(String firstdate, String lastdate) {
        this.firstdate = firstdate;
        this.lastdate = lastdate;
    }
}
