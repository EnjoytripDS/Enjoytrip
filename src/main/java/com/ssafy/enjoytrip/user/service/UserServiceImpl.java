package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.dao.UserDao;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedEmailException;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedNicknameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String email, String password) {
        User user = userDao.findByEmail(email);
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
        userDao.insertUser(user);
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
        int dupCnt = userDao.existsByEmail(email);
        if (dupCnt > 0) {
            throw new UserDuplicatedEmailException();
        }
    }

    @Override
    public void checkDupNickname(String nickname) {
        int dupCnt = userDao.existsByNickname(nickname);
        if (dupCnt > 0) {
            throw new UserDuplicatedNicknameException();
        }
    }
}
