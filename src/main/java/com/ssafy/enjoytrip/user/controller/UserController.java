package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.dto.request.CreateUserRequest;
import com.ssafy.enjoytrip.user.dto.request.LoginRequest;
import com.ssafy.enjoytrip.user.dto.request.ModifyUserRequest;
import com.ssafy.enjoytrip.user.dto.request.UserEmailRequest;
import com.ssafy.enjoytrip.user.dto.request.UserNicknameRequest;
import com.ssafy.enjoytrip.user.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public void login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        User loginUser = userService.login(request.getEmail(), request.getPassword());


    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable("id") int userId) {
        return new ResponseEntity<>(userService.findMyPage(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public int modifyUserInfo(@PathVariable("id") int userId,
            @RequestBody ModifyUserRequest request) {
        User userInfo = request.toDto();
        userInfo.setId(userId);
        return userService.modify(userInfo);
    }

    @DeleteMapping("/{id}")
    public int dropOutUser(@PathVariable("id") int userId) {
        return userService.deleteById(userId);
    }
}
