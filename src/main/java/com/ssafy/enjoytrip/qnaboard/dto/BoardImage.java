package com.ssafy.enjoytrip.qnaboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardImage {

    private int id;
    private String pathName;
    private String fileName;
    private String imageName;
    private int size;
    private String imageType;
    private String imageUrl;
    private int qnaBoardId;
    private String createtime;

}
