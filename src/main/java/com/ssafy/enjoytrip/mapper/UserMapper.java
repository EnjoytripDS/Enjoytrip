package com.ssafy.enjoytrip.mapper;

import com.ssafy.enjoytrip.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(User user);
}
