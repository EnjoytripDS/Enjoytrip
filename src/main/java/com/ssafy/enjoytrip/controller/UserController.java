package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.controller.request.UserCreateRequest;
import com.ssafy.enjoytrip.service.UserService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserCreateRequest request) {
        userService.signup(request.toDto());
        return ResponseEntity.ok().body(null);

    }

}
