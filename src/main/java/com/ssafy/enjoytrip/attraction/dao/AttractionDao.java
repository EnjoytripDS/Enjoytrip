package com.ssafy.enjoytrip.attraction.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao {

    List<String> getGugun(int sidoCode);
}
