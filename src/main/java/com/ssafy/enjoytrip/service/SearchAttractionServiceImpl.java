package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.mapper.SearchAttractionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchAttractionServiceImpl implements SearchAttractionService {

    private final SearchAttractionMapper searchAttractionMapper;

    public SearchAttractionServiceImpl(SearchAttractionMapper searchAttractionMapper) {
        this.searchAttractionMapper = searchAttractionMapper;
    }

    @Override
    public List<String> getGugun(int sidoCode) {
        return searchAttractionMapper.getGugun(sidoCode);
    }
}
