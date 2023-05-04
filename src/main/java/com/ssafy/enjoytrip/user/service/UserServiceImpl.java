package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.dao.UserDao;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.exception.InvalidPasswordException;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedEmailException;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedNicknameException;
import com.ssafy.enjoytrip.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String email, String password) {
        User user = userDao.selectOneByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!passwordCheck(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    private boolean passwordCheck(String rawPassword, String password) {
        return passwordEncoder.matches(rawPassword, password);
    }

    @Transactional
    @Override
    public void signup(User user) {
        checkDupEmail(user.getEmail());
        checkDupNickname(user.getNickname());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.insert(user);
    }

    @Transactional
    @Override
    public int modify(User userInfo) {
        log.info("userid: ", userInfo.getId());
        String originalPassword = userDao.findById(userInfo.getId()).getPassword();
        log.info("originalPassword: ", originalPassword);
        // 비밀번호 확인
        if (!passwordCheck(userInfo.getPassword(), originalPassword)) {
            throw new InvalidPasswordException();
        }
        return userDao.update(userInfo);
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        return userDao.deleteById(id);
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
