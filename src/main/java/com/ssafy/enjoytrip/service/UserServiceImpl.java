package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.user.User;
import com.ssafy.enjoytrip.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User login(String id, String password) {
        return null;
    }

    @Transactional
    @Override
    public void signup(User user) {
        userMapper.insertUser(user);
    }

    @Transactional
    @Override
    public int modify(User user) {
        return 0;
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        return 0;
    }
}
