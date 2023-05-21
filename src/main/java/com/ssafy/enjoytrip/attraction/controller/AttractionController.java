package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import com.ssafy.enjoytrip.attraction.service.AttractionService;
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
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/find/gugun/{sidoCode}")
    public ResponseEntity<List<String>> getGugun(@PathVariable("sidoCode") int sidoCode) {
        List<String> gugunList = attractionService.getGugun(sidoCode);
        return new ResponseEntity<List<String>>(gugunList, HttpStatus.OK);
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
    public ResponseEntity<List<Attraction>> search(
            @RequestParam(value = "sidoCode", defaultValue = "0") int sidoCode,
            @RequestParam(value = "gugunCode",defaultValue = "0") int gugunCode,
            @RequestParam(value = "contentTypeId", required = false) List<Integer>  contentTypeIdList,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", defaultValue = "name") String sort
    ) {
        List<Attraction> attractionList = attractionService.search(
                new SearchCondition(
                        sidoCode,
                        gugunCode,
                        contentTypeIdList,
                        keyword,
                        sort
                )
        );
        return new ResponseEntity<>(attractionList, HttpStatus.OK);
    }

}
