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

    private int content_id; //primary key
    private int content_type_id;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String first_image;
    private String first_image2;
    private int readcount;
    private int sido_code; //primary key (sido table)
    private int gugun_code; //primary key (gugun table)
    private double latitude;
    private double longitude;
    private String mlevel; //attraction_info table
}
