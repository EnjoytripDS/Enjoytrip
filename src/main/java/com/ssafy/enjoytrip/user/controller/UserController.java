package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.commons.jwt.service.JwtService;
import com.ssafy.enjoytrip.user.dto.User;
import com.ssafy.enjoytrip.user.dto.request.CreateUserRequest;
import com.ssafy.enjoytrip.user.dto.request.LoginRequest;
import com.ssafy.enjoytrip.user.dto.request.ModifyUserRequest;
import com.ssafy.enjoytrip.user.dto.request.UserEmailRequest;
import com.ssafy.enjoytrip.user.dto.request.UserNicknameRequest;
import com.ssafy.enjoytrip.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final UserService userService;

    @Autowired
    private JwtService jwtService;


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
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @Valid LoginRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            User loginUser = userService.login(request.getEmail(), request.getPassword());
            if (loginUser != null) {
                String accessToken = jwtService.createAccessToken("userid",
                        loginUser.getId());// key, data
                String refreshToken = jwtService.createRefreshToken("userid",
                        loginUser.getId());// key, data
                log.info("로그인 accessToken 정보 : {}", accessToken);
                log.info("로그인 refreshToken 정보 : {}", refreshToken);
                userService.saveRefreshToken(loginUser.getId(), refreshToken);
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } else {
                resultMap.put("message", FAIL);
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable("id") int userId,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            logger.info("사용 가능한 토큰");
            try {
                User user = userService.getUserInfo(userId);
                resultMap.put("userInfo", user);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰");
            resultMap.put("message", FAIL);
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/{id}")
    public int modifyUserInfo(@PathVariable("id") int userId,
            @RequestBody ModifyUserRequest request) {
        User userInfo = request.toDto();
        userInfo.setId(userId);
        return userService.modify(userInfo);
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable("id") int id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            userService.deleteRefreshToken(id);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody User user,
            HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refresh-token");
        logger.debug("token : {}, memberDto : {}", token, user);
        if (jwtService.checkToken(token)) {
            if (token.equals(userService.getRefreshToken(user.getId()))) {
                String accessToken = jwtService.createAccessToken("userid", user.getId());
                logger.debug("token : {}", accessToken);
                logger.debug("정상적으로 액세스토큰 재발급");
                resultMap.put("access-token", accessToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            }
        } else {
            logger.debug("리프레쉬토큰도 사용불");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/{id}")
    public int dropOutUser(@PathVariable("id") int userId,
            @RequestBody @Valid String password, HttpServletRequest request) {
        return userService.dropOutById(userId, password);
    }
}
