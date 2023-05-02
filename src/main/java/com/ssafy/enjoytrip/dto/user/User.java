package com.ssafy.enjoytrip.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String email;
    private String password;
    private String name; //실명 아님
    private UserStatus status;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;


    public User(String email, String password, String name) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.status = UserStatus.COMMON;
        this.createtime = LocalDateTime.now();
        this.updatetime = LocalDateTime.now();
    }
}
