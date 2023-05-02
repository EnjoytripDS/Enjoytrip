package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.controller.request.GetGugunRequest;
import com.ssafy.enjoytrip.service.SearchAttractionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<String>> getGugun(@RequestBody GetGugunRequest request) {
        List<String> gugunCodeList = new ArrayList<>();
        gugunCodeList = searchAttractionService.getGugun(request.GetGugun());
        ResponseEntity<List<String>> re = new ResponseEntity<>(gugunCodeList, HttpStatus.OK);
        return re;
    }
}
