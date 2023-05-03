package com.ssafy.enjoytrip.user.dto.request;

import com.ssafy.enjoytrip.user.dto.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateUserRequest {

    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Pattern(regexp = "^[0-9a-z].{6,10}$", message = "영문 소문자, 숫자 6~10자 이내로 입력하세요.”")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    private String nickname;


    public User toDto() {
        return new User(email, password, nickname);
    }
}
