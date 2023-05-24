package com.ssafy.enjoytrip.trip.controller;

import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.trip.dto.Trip;
import com.ssafy.enjoytrip.trip.dto.request.CreateTripRequest;
import com.ssafy.enjoytrip.trip.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/my-trip")
@Api(tags = "내 여행 API")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    @ApiOperation(value = "여행 생성", notes = "여행을 생성할 수 있습니다.")
    public CommonApiResponse<String> createTrip(@RequestBody Trip request) { // TODO request로 변경
        tripService.makeTrip(request);
        return CommonApiResponse.success("ok");
    }


}
