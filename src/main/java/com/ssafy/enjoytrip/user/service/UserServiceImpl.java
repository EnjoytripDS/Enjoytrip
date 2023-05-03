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
        return user;
    }

    private boolean passwordCheck(User user, String password) {
        return true;
    }

    @Transactional
    @Override
    public void signup(User user) {
        userDao.insert(user);
    }

    @Transactional
    @Override
    public int modify(int userId, User userinfo) {
        // 비밀번호 확인
        if (!passwordCheck(userinfo, userinfo.getPassword())) {
            throw new RuntimeException();
        }
        userinfo.setId(userId);
        return userDao.update(userinfo);
    }

    @Override
    public int modify(User user) {
        return userDao.update(user);
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

    @Override
    public User findMyPage(int id) {
        return userDao.findById(id);
    }


}
