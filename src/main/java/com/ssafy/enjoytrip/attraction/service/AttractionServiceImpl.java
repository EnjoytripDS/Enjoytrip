package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.legacy.mapper.SearchAttractionMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final SearchAttractionMapper searchAttractionMapper;

    public AttractionServiceImpl(SearchAttractionMapper searchAttractionMapper) {
        this.searchAttractionMapper = searchAttractionMapper;
    }

    @Override
    public List<String> getGugun(int sidoCode) {
        return searchAttractionMapper.getGugun(sidoCode);
    }
}
