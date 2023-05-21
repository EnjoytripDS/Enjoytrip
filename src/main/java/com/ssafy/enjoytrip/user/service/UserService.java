package com.ssafy.enjoytrip.user.service;


import com.ssafy.enjoytrip.user.dto.User;

public interface UserService {

    User login(String email, String password);

    public void saveRefreshToken(int userid, String refreshToken) throws Exception;

    public Object getRefreshToken(int userid) throws Exception;

    public void deleteRefreshToken(int userid) throws Exception;

    void signup(User user);

    int modify(User user);

    int dropOutById(int id, String password);

    void checkDupEmail(String email);

    void checkDupNickname(String nickname);

    User getUserInfo(int id);
}
