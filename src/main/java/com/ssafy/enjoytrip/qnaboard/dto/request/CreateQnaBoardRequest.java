package com.ssafy.enjoytrip.qnaboard.dto.request;

import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateQnaBoardRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String nickname;
    @NotBlank
    private String content;

    public QnaBoardView toViewDto() {
        return new QnaBoardView(title, content, nickname);
    }
}
