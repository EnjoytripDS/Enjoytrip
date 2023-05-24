package com.ssafy.enjoytrip.trip.dto.request;

import com.ssafy.enjoytrip.trip.dto.Trip;
import com.ssafy.enjoytrip.trip.dto.TripAttraction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateTripRequest {

    private int firstdate;
    private int lastdate;
    private List<TripAttraction> tripAttractionRequestList;

    public Trip toDto() {
        return new Trip(
                this.firstdate,
                this.lastdate,
                this.tripAttractionRequestList);

    }


}
