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

    @GetMapping("/{sidoCode}")
    public ResponseEntity<List<String>> getGugun(@PathVariable("sidoCode") int sidoCode) {
        List<String> gugunList = attractionService.getGugun(sidoCode);
        return new ResponseEntity<List<String>>(gugunList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Attraction>> search(
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
        return new ResponseEntity<>(attractionList, HttpStatus.OK);
    }

    @GetMapping("/{attractionId}")
    public ResponseEntity<List<Attraction>> attractionDetail(
            @PathVariable("attractionId") int attractionId
    ) {
        return new ResponseEntity<>(attractionService.readAttractionDetails(attractionId), HttpStatus.OK);
    }

}
