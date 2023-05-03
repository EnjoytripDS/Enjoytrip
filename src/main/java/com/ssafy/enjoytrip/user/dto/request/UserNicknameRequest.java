package com.ssafy.enjoytrip.user.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserNicknameRequest {

    @NotBlank
    private String nickname;

}
