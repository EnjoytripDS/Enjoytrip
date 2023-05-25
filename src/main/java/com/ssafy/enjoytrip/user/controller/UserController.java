package com.ssafy.enjoytrip.user.controller;


import static com.ssafy.enjoytrip.commons.exception.ErrorCode.COMMON_SYSTEM_ERROR;
import static com.ssafy.enjoytrip.commons.exception.ErrorCode.PASSWORD_NOT_MATCHED;
import static com.ssafy.enjoytrip.commons.exception.ErrorCode.USER_NOT_FOUND;

import com.ssafy.enjoytrip.commons.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.commons.jwt.service.JwtService;
import com.ssafy.enjoytrip.user.exception.InvalidPasswordException;
import com.ssafy.enjoytrip.user.exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.dto.request.CreateUserRequest;
import com.ssafy.enjoytrip.user.dto.request.LoginRequest;
import com.ssafy.enjoytrip.user.dto.request.ModifyPwdRequest;
import com.ssafy.enjoytrip.user.dto.request.ModifyUserRequest;
import com.ssafy.enjoytrip.user.dto.request.UserEmailRequest;
import com.ssafy.enjoytrip.user.dto.request.UserNicknameRequest;
import com.ssafy.enjoytrip.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "유저 API")
public class UserController {

    private final UserService userService;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtService jwtService;

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
    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호 request를 받아 로그인합니다.")
    public CommonApiResponse<Map<String, Object>> login(
            @RequestBody LoginRequest request
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User loginUser = userService.login(request.getEmail(), request.getPassword());
            if (loginUser != null) {
                String accessToken = jwtService.createAccessToken("userid",
                        loginUser.getId());// key, data
                String refreshToken = jwtService.createRefreshToken("userid",
                        loginUser.getId());// key, data
                userService.saveRefreshToken(loginUser.getId(), refreshToken);
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                resultMap.put("message", SUCCESS);
            }
        } catch (Exception e) {
            if (e instanceof InvalidPasswordException) {
                return CommonApiResponse.fail(PASSWORD_NOT_MATCHED);
            } else if (e instanceof UserNotFoundException) {
                return CommonApiResponse.fail(USER_NOT_FOUND);
            } else {
                return CommonApiResponse.fail(COMMON_SYSTEM_ERROR);
            }
        }
        return CommonApiResponse.success(resultMap);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "유저 정보 읽기", notes = "유저 id에 해당하는 유저 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "4")
    })
    public CommonApiResponse<Map<String, Object>> getUserInfo(@PathVariable("id") int userId,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            try {
                User user = userService.getUserInfo(userId);
                resultMap.put("userInfo", user);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            resultMap.put("message", FAIL);
            status = HttpStatus.UNAUTHORIZED;
        }
        return CommonApiResponse.success(resultMap);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "유저 정보 수정", notes = "유저 id에 해당하는 유저 정보를 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "4")
    })
    public CommonApiResponse<String> modifyUserInfo(@PathVariable("id") int userId,
            @RequestBody ModifyUserRequest request) {
        User userInfo = request.toDto();
        userInfo.setId(userId);
        userService.modify(userInfo);
        return CommonApiResponse.success("ok");
    }

    @PutMapping("/password")
    @ApiOperation(value = "유저 비밀번호 수정", notes = "유저 id에 해당하는 유저의 비밀번호를 수정할 수 있습니다.")
    public CommonApiResponse<String> modifyUserPwd(@RequestBody @Valid ModifyPwdRequest request) {
        userService.modifyPwd(request.getUserId(), request.getCurPwd(), request.getNewPwd());
        return CommonApiResponse.success("ok");
    }

    @GetMapping("/logout/{id}")
    @ApiOperation(value = "로그아웃", notes = "로그인한 유저 정보를 제거하여 로그아웃합니다.")
    public CommonApiResponse<?> logout(@PathVariable("id") int id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            userService.deleteRefreshToken(id);
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
        }
        return CommonApiResponse.success(resultMap);
    }

    @PostMapping("/refresh")
    public CommonApiResponse<?> refreshToken(@RequestBody User user,
            HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String token = request.getHeader("refresh-token");
        if (jwtService.checkToken(token)) {
            if (token.equals(userService.getRefreshToken(user.getId()))) {
                String accessToken = jwtService.createAccessToken("userid", user.getId());
                resultMap.put("access-token", accessToken);
                resultMap.put("message", SUCCESS);
            }
        } else {
            log.info("토큰 인증 실패");
        }
        return CommonApiResponse.success(resultMap);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "유저 삭제", notes = "유저 id에 해당하는 유저 정보를 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 id", example = "4")
    })
    public CommonApiResponse<String> delete(@PathVariable("id") int userId,
            @RequestBody String checkPassword,
            HttpServletRequest request) {
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            userService.dropOutById(userId, checkPassword);
        }
        return CommonApiResponse.success("ok");
    }
}
