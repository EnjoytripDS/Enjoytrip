package com.ssafy.enjoytrip.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private int role;
    private String createtime;
    private String updatetime;


    public User(String email, String password, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = 1;
    }

    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
        this.role = 1;
    }

    public User(String password) {
        this.password = password;
        this.role = 1;
    }
}
