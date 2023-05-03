package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.service.SearchAttractionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/search")
public class SearchAttractionController {

    private final SearchAttractionService searchAttractionService;

    public SearchAttractionController(SearchAttractionService searchAttractionService) {
        this.searchAttractionService = searchAttractionService;
    }

    @GetMapping("/{sidoCode}")
    public ResponseEntity<List<String>> getGugun(@PathVariable("sidoCode") int sidoCode) {
        List<String> gugunList = searchAttractionService.getGugun(sidoCode);
        return new ResponseEntity<List<String>>(gugunList, HttpStatus.OK);
    }
}
