package com.ssafy.enjoytrip.legacy.mapper;

import com.ssafy.enjoytrip.legacy.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    int existsByEmail(String email);

    int existsByNickname(String nickname);

    User findByEmail(String email);
}
