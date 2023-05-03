package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.dao.AttractionDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionDao attractionDao;

    public AttractionServiceImpl(AttractionDao attractionDao) {
        this.attractionDao = attractionDao;
    }

    @Override
    public List<String> getGugun(int sidoCode) {
        return attractionDao.getGugun(sidoCode);
    }
}
