package com.ssafy.enjoytrip.user.dao;

import com.ssafy.enjoytrip.legacy.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    void insertUser(User user);

    int existsByEmail(String email);

    int existsByNickname(String nickname);

    User findByEmail(String email);
}
