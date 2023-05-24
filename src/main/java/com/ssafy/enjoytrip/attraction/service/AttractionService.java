package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.Gugun;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import java.util.List;

public interface AttractionService {

    List<Gugun> getGugun(int sidoCode);

    List<Attraction> search(SearchCondition searchCondition);

    Attraction readAttractionDetails(int attractionId);
}
