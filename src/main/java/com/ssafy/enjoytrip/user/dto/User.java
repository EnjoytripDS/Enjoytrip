package com.ssafy.enjoytrip.user.dto;

import java.time.LocalDateTime;
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
    private UserRole role;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;


    public User(String email, String password, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = UserRole.COMMON;
        this.createtime = LocalDateTime.now();
        this.updatetime = LocalDateTime.now();
    }
}
