package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.user.User;

public interface UserService {

    User login(String id, String password);

    void signup(User user);

    int modify(User user);

    int deleteById(int id);


    void checkDupEmail(String email);

    void checkDupNickname(String nickname);
}
