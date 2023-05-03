package com.ssafy.enjoytrip.legacy.service;

import com.ssafy.enjoytrip.legacy.mapper.SearchAttractionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchAttractionServiceImpl implements AttractionService {

    private final SearchAttractionMapper searchAttractionMapper;

    public SearchAttractionServiceImpl(SearchAttractionMapper searchAttractionMapper) {
        this.searchAttractionMapper = searchAttractionMapper;
    }

    @Override
    public List<String> getGugun(int sidoCode) {
        return searchAttractionMapper.getGugun(sidoCode);
    }
}
