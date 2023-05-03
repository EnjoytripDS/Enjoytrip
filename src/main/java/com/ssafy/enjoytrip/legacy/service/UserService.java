package com.ssafy.enjoytrip.legacy.service;

import com.ssafy.enjoytrip.legacy.dto.user.User;

public interface UserService {

    User login(String id, String password);

    void signup(User user);

    int modify(User user);

    int deleteById(int id);


    void checkDupEmail(String email);

    void checkDupNickname(String nickname);
}
