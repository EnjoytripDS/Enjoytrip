package com.ssafy.enjoytrip.user.dao;

import com.ssafy.enjoytrip.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    void insert(User user);

    int existsByEmail(String email);

    int existsByNickname(String nickname);

    User findByEmail(String email);

    User findById(int id);

    int update(User user);

    int deleteById(int id);
}
