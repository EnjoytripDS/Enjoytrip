package com.ssafy.enjoytrip.qnaboard.dto.request;

import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateBoardCommentRequest {

    @NotBlank
    private String nickname;
    @NotBlank
    private String content;

    public BoardComment toViewDto() {
        return new BoardComment(content, nickname);
    }
}
