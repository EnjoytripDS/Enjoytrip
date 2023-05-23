package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import com.ssafy.enjoytrip.attraction.service.AttractionService;
import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "구/군 정보 조회", notes = "선택한 시/도에 해당하는 유저 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sidoCode", value = "시/도 코드", example = "1")
    })
    public CommonApiResponse<List<String>> getGugun(@PathVariable("sidoCode") int sidoCode) {
        List<String> gugunList = attractionService.getGugun(sidoCode);
        return CommonApiResponse.success(gugunList);
    }

    /**
     *
     * @param sidoCode : 시/도 (단일 선택 / 전체: 0)
     * @param gugunCode : 구/군 (단일 선택 / 전체:0)
     * @param contentTypeIdList : 관광지 유형 (복수선택 가능 / 전체: null)
     * @param keyword : 검색 키워드
     * @param sort : 정렬 기준
     * @return : 관광지 리스트
     */
    @GetMapping
    @ApiOperation(value = "관광지 검색", notes = "시/도, 구/군, 관광지 유형, 키워드 정보를 통해 해당하는 관광지들을 검색하여 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sidoCode", value = "시/도 코드", example = "6"),
            @ApiImplicitParam(name = "gugunCode", value = "구/군 코드", example = "16"),
            @ApiImplicitParam(name = "contentTypeId", value = "관광지 유형", example = "32,39"),
            @ApiImplicitParam(name = "keyword", value = "검색 키워드", example = "해운대")
    })
    public CommonApiResponse<List<Attraction>> search(
            @RequestParam(value = "sidoCode", defaultValue = "0") int sidoCode,
            @RequestParam(value = "gugunCode",defaultValue = "0") int gugunCode,
            @RequestParam(value = "contentTypeId", required = false) List<Integer>  contentTypeIdList,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        List<Attraction> attractionList = attractionService.search(
                new SearchCondition(
                        sidoCode,
                        gugunCode,
                        contentTypeIdList,
                        keyword
                )
        );
        return CommonApiResponse.success(attractionList);
    }

    @GetMapping("/{attractionId}")
    @ApiOperation(value = "관광지 상세 정보 조회", notes = "관광지 id에 해당하는 관광지 상세 정보를 조회 할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attractionId", value = "관광지 id(content_id)", example = "125533")
    })
    public CommonApiResponse<Attraction> attractionDetail(
            @PathVariable("attractionId") int attractionId
    ) {
        Attraction attractionInfos = attractionService.readAttractionDetails(attractionId);
        return CommonApiResponse.success(attractionInfos);
    }

}
