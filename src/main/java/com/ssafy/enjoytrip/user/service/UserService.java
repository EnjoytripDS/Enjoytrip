package com.ssafy.enjoytrip.user.service;


import com.ssafy.enjoytrip.user.dto.User;

public interface UserService {

    User login(String email, String password);

    void signup(User user);

    int modify(User user);

    int dropOutById(int id);

    void checkDupEmail(String email);

    void checkDupNickname(String nickname);

    User getUserInfo(int id);
}
