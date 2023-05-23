package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import com.ssafy.enjoytrip.attraction.service.AttractionService;
import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/search")
@Api(tags = "관광지 API")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/{sidoCode}/gugun")
    public CommonApiResponse<List<String>> getGugun(@PathVariable("sidoCode") int sidoCode) {
        List<String> gugunList = attractionService.getGugun(sidoCode);
        return CommonApiResponse.success(gugunList);
    }

    // TODO 정렬 (프론트에서 구현한 후 적용 예정)
    @GetMapping
    public CommonApiResponse<List<Attraction>> search(
            @RequestParam("sidoCode") int sidoCode,
            @RequestParam("gugunCode") int gugunCode,
            @RequestParam("contentTypeId") int contentTypeId,
            @RequestParam("keyword") String keyword
    ) {
        List<Attraction> attractionList = attractionService.search(
                new SearchCondition(
                        sidoCode,
                        gugunCode,
                        contentTypeId,
                        keyword
                )
        );
        return CommonApiResponse.success(attractionList);
    }

    @GetMapping("/{attractionId}")
    public CommonApiResponse<Attraction> attractionDetail(
            @PathVariable("attractionId") int attractionId
    ) {
        Attraction attractionInfos = attractionService.readAttractionDetails(attractionId);
        return CommonApiResponse.success(attractionInfos);
    }

}
