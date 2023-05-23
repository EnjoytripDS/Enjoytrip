package com.ssafy.enjoytrip.attraction.dao;

import com.ssafy.enjoytrip.attraction.dto.Attraction;
import com.ssafy.enjoytrip.attraction.dto.SearchCondition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao {

    List<String> getGugun(int sidoCode);

    List<Attraction> selectAllByCondition(SearchCondition searchCondition);

    Attraction selectOneById(int attractionId);
}
