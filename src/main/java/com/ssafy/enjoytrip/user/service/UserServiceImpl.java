package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.dao.UserDao;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.exception.InvalidPasswordException;
import com.ssafy.enjoytrip.user.exception.PasswordFailException;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedEmailException;
import com.ssafy.enjoytrip.user.exception.UserDuplicatedNicknameException;
import com.ssafy.enjoytrip.user.exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
    public User login(String email, String rawPassword) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!passwordCheck(rawPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    private boolean passwordCheck(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void saveRefreshToken(int userid, String refreshToken) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userid);
        map.put("token", refreshToken);
        userDao.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(int userid) throws Exception {
        return userDao.getRefreshToken(userid);
    }

    @Override
    public void deleteRefreshToken(int userid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("token", null);
        userDao.deleteRefreshToken(map);
    }

    @Transactional
    @Override
    public void signup(User user) {
        checkDupEmail(user.getEmail());
        checkDupNickname(user.getNickname());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println(encodedPassword);
        user.setPassword(encodedPassword);
        userDao.insert(user);
    }

    @Transactional
    @Override
    public int modify(User userInfo) {
//        // 기존 유저의 비밀번호와 회원정보 수정을 위해 입력한 비밀번호가 일치하는지 확인
//        String originalPassword = userDao.findById(userInfo.getId()).getPassword();
//        // 일치하지 않을 경우, 수정 불가 에러 리턴
//        if (!passwordCheck(userInfo.getPassword(), originalPassword)) {
//            throw new PasswordFailException();
//        }
//        // 일치할 경우
        checkDupNickname(userInfo.getNickname());
        return userDao.update(userInfo);
    }

    @Transactional
    @Override
    public int dropOutById(int id, String password) {
        // 기존 유저의 비밀번호와 회원정보 수정을 위해 입력한 비밀번호가 일치하는지 확인
        String originalPassword = userDao.findById(id).getPassword();
        // 일치하지 않을 경우, 수정 불가 에러 리턴
        if (!passwordCheck(password, originalPassword)) {
            throw new PasswordFailException();
        }
        // 일치할 경우
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
    public User getUserInfo(int id) {
        return userDao.findById(id);
    }


}
