package com.ssafy.enjoytrip.trip.service;

import com.ssafy.enjoytrip.trip.dto.Trip;
import com.ssafy.enjoytrip.trip.dto.TripAttraction;
import java.util.List;

public interface TripService {

    int makeTrip(Trip trip);

    void makeTripAttractions(List<TripAttraction> request);
}
