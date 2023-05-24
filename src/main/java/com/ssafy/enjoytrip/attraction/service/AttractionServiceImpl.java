package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.dao.AttractionDao;
import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.Gugun;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionDao attractionDao;

    public AttractionServiceImpl(AttractionDao attractionDao) {
        this.attractionDao = attractionDao;
    }

    @Override
    public List<Gugun> getGugun(int sidoCode) {
        return attractionDao.getGugun(sidoCode);
    }

    @Override
    public List<Attraction> search(SearchCondition searchCondition) {
        return attractionDao.selectAllByCondition(searchCondition);
    }

    @Override
    public Attraction readAttractionDetails(int attractionId) {
        return attractionDao.selectOneById(attractionId);
    }

}
