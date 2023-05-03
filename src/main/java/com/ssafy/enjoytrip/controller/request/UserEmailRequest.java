package com.ssafy.enjoytrip.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserEmailRequest {

    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

}
