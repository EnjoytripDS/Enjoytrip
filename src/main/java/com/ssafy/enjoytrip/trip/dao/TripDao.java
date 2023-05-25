package com.ssafy.enjoytrip.trip.dao;

import com.ssafy.enjoytrip.trip.dto.Trip;
import com.ssafy.enjoytrip.trip.dto.TripAttraction;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripDao {

    public void insertTrip(Trip trip);
    public void insertTripAttraction(List<TripAttraction> tripAttractionList);
}
