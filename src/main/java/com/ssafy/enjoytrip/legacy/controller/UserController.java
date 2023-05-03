package com.ssafy.enjoytrip.legacy.controller;

import com.ssafy.enjoytrip.legacy.controller.request.CreateUserRequest;
import com.ssafy.enjoytrip.legacy.controller.request.LoginRequest;
import com.ssafy.enjoytrip.legacy.controller.request.UserEmailRequest;
import com.ssafy.enjoytrip.legacy.controller.request.UserNicknameRequest;
import com.ssafy.enjoytrip.legacy.dto.user.User;
import com.ssafy.enjoytrip.legacy.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid CreateUserRequest request) {
        userService.signup(request.toDto());
        return ResponseEntity.ok().body(null);

    }

    @PostMapping("/check/email")
    public ResponseEntity<Void> checkEmail(@RequestBody @Valid UserEmailRequest request) {
        userService.checkDupEmail(request.getEmail());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<Void> checkNickname(@RequestBody @Valid UserNicknameRequest request) {
        userService.checkDupNickname(request.getNickname());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        User loginUser = userService.login(request.getEmail(), request.getPassword());


    }


}
