package com.ssafy.enjoytrip.legacy.service;

import com.ssafy.enjoytrip.legacy.dto.user.User;
import com.ssafy.enjoytrip.legacy.exception.UserDuplicatedEmailException;
import com.ssafy.enjoytrip.legacy.exception.UserDuplicatedNicknameException;
import com.ssafy.enjoytrip.legacy.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User login(String email, String password) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new RuntimeException();
        }
        if (!passwordCheck(user, password)) {
            throw new RuntimeException();
        }
        return null;
    }

    private boolean passwordCheck(User user, String password) {
        return true;
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

    @Override
    public void checkDupEmail(String email) {
        int dupCnt = userMapper.existsByEmail(email);
        if (dupCnt > 0) {
            throw new UserDuplicatedEmailException();
        }
    }

    @Override
    public void checkDupNickname(String nickname) {
        int dupCnt = userMapper.existsByNickname(nickname);
        if (dupCnt > 0) {
            throw new UserDuplicatedNicknameException();
        }
    }
}
