package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.dto.request.CreateUserRequest;
import com.ssafy.enjoytrip.user.dto.request.LoginRequest;
import com.ssafy.enjoytrip.user.dto.request.UserEmailRequest;
import com.ssafy.enjoytrip.user.dto.request.UserNicknameRequest;
import com.ssafy.enjoytrip.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.StandardSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserRequest request) {
        userService.signup(request.toDto());
        return ResponseEntity.ok().body("회원가입 완료");

    }

    @PostMapping("/check/email")
    public ResponseEntity<String> checkEmail(@RequestBody @Valid UserEmailRequest request) {
        userService.checkDupEmail(request.getEmail());
        return ResponseEntity.ok().body("이메일 중복 체크 완료");
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<String> checkNickname(@RequestBody @Valid UserNicknameRequest request) {
        userService.checkDupNickname(request.getNickname());
        return ResponseEntity.ok().body("닉네임 중복 체크 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        User loginUser = userService.login(request.getEmail(), request.getPassword());
        session.setAttribute("loginUser", loginUser);
        return ResponseEntity.ok().body("로그인 완료");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseEntity.ok().body("로그아웃 완료");
    }


}
