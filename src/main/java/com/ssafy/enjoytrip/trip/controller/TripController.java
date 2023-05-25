package com.ssafy.enjoytrip.trip.controller;

import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.trip.dto.Trip;
import com.ssafy.enjoytrip.trip.dto.TripAttraction;
import com.ssafy.enjoytrip.trip.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ApiOperation(value = "여행 생성", notes = "여행을 생성할 수 있습니다. 반환값은 생성된 여행 id입니다.")
    public CommonApiResponse<Integer> createTrip(
            @RequestBody Trip request) { // 추후 request로 리팩토링 (시간없어서 dto로 바로 넘겨줌)
        tripService.makeTrip(request);
        return CommonApiResponse.success(request.getId());
    }

    @PostMapping("/{tripId}/tripAttraction")
    @ApiOperation(value = "선택한 여행 관광지를 여행에 추가", notes = "내 여행에 가고자 하는 여행지를 추가합니다. trip_attraction 생성. pathVariable은 tripId")
    public CommonApiResponse<String> createTripAttraction(
            @PathVariable int tripId,
            @RequestBody List<TripAttraction> request) { // 추후 request로 리팩토링 (시간없어서 dto로 바로 넘겨줌)
        for (int i = 0; i < request.size(); i++) {
            System.out.println(request.get(i));
        }
        tripService.makeTripAttractions(request);
        return CommonApiResponse.success("ok");
    }

}
