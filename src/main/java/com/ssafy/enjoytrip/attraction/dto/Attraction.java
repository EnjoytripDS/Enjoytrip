package com.ssafy.enjoytrip.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attraction {

    private int id; //primary key
    private int contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private int readcount;
    private int sidoCode; //primary key (sido table)
    private int gugunCode; //primary key (gugun table)
    private double latitude;
    private double longitude;
    private String mlevel; //attraction_info table
}
