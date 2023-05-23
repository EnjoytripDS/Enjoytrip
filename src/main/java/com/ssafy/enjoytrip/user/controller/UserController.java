package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.dto.request.CreateUserRequest;
import com.ssafy.enjoytrip.user.dto.request.LoginRequest;
import com.ssafy.enjoytrip.user.dto.request.ModifyUserRequest;
import com.ssafy.enjoytrip.user.dto.request.UserEmailRequest;
import com.ssafy.enjoytrip.user.dto.request.UserNicknameRequest;
import com.ssafy.enjoytrip.user.dto.response.LoginResponse;
import com.ssafy.enjoytrip.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "유저 API")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "회원가입", notes = "이메일, 비밀번호, 닉네임 request를 받아 회원가입합니다.")
    public CommonApiResponse<String> signUp(@RequestBody @Valid CreateUserRequest request) {
        userService.signup(request.toDto());
        return CommonApiResponse.success("ok");
    }


    @PostMapping("/check/email")
    @ApiOperation(value = "이메일 중복체크", notes = "이미 존재하는 이메일인지 중복체크 합니다.")
    public CommonApiResponse<String> checkEmail(@RequestBody @Valid UserEmailRequest request) {
        userService.checkDupEmail(request.getEmail());
        return CommonApiResponse.success("ok");
    }

    @PostMapping("/check/nickname")
    @ApiOperation(value = "닉네임 중복체크", notes = "이미 존재하는 닉네임인지 중복체크 합니다.")
    public CommonApiResponse<String> checkNickname(
            @RequestBody @Valid UserNicknameRequest request) {
        userService.checkDupNickname(request.getNickname());
        return CommonApiResponse.success("ok");
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호 request를 받아 세션 방식으로 로그인합니다.")
    public CommonApiResponse<LoginResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpSession session
    ) {
        User loginUser = userService.login(request.getEmail(), request.getPassword());
        session.setAttribute("loginUser", loginUser);
        return CommonApiResponse.success(new LoginResponse(loginUser.getId(), session.getId()));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "유저 정보 읽기", notes = "유저 id에 해당하는 유저 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "1")
    })
    public CommonApiResponse<User> getUserInfo(@PathVariable("id") int userId,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("JSESSIONID: ", session.getId());
        log.info("세션 유저 정보: ", session.getAttribute("loginUser"));
        User userInfo = userService.getUserInfo(userId);
        return CommonApiResponse.success(userInfo);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "유저 정보 수정", notes = "유저 id에 해당하는 유저 정보를 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "1")
    })
    public CommonApiResponse<String> modifyUserInfo(@PathVariable("id") int userId,
            @RequestBody ModifyUserRequest request) {
        User userInfo = request.toDto();
        userInfo.setId(userId);
        userService.modify(userInfo);
        return CommonApiResponse.success("ok");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그인한 유저 세션 정보를 제거하여 로그아웃합니다.")
    public CommonApiResponse<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("JSESSIONID: ", session.getId());
            log.info("세션 유저 정보: ", session.getAttribute("loginUser"));
            session.invalidate();
        }
        return CommonApiResponse.success("ok");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "유저 삭제", notes = "유저 id에 해당하는 유저 정보를 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "1")
    })
    public CommonApiResponse<String> delete(@PathVariable("id") int userId) {
        userService.dropOutById(userId);
        return CommonApiResponse.success("ok");
    }
}
