package com.ssafy.enjoytrip.trip.dao;

import com.ssafy.enjoytrip.trip.dto.Trip;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripDao {

    public void insertTrip(Trip trip);

}
