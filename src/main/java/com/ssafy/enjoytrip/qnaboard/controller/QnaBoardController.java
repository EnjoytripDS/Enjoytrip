package com.ssafy.enjoytrip.qnaboard.controller;

import com.ssafy.enjoytrip.commons.jwt.service.JwtService;
import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import com.ssafy.enjoytrip.qnaboard.dto.request.CreateQnaBoardRequest;
import com.ssafy.enjoytrip.qnaboard.service.QnaBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/qna-board")
@Api(tags = "Q&A 게시판 API")
public class QnaBoardController {

    private static final String SUCCESS = "succes";
    private static final String FAIL = "fail";

    private final QnaBoardService qnaBoardService;

    @Autowired
    private JwtService jwtService;

    public QnaBoardController(QnaBoardService qnaBoardService) {
        this.qnaBoardService = qnaBoardService;
    }

    @GetMapping
    @ApiOperation(value = "게시물 목록 조회", notes = "모든 게시물을 조회할 수 있습니다.")
    public CommonApiResponse<List<QnaBoardView>> list(HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        List<QnaBoardView> result = new ArrayList<>();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            result = qnaBoardService.getBoardList();
        }
        return CommonApiResponse.success(result);
    }

    @GetMapping("/search")
    @ApiOperation(value = "게시물 검색", notes = "전체/글 제목/내용/작성자를 기준으로 게시물을 검색할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = "검색 기준", example = "4"),
            @ApiImplicitParam(name = "keyword", value = "검색 키워드", example = "다정천사"),

    })
    public ResponseEntity<List<QnaBoardView>> search(
            @RequestParam(defaultValue = "") String mode,
            @RequestParam(defaultValue = "") String keyword
    ) {
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("mode", mode);
        params.put("keyword", keyword);

        return new ResponseEntity<List<QnaBoardView>>(
                qnaBoardService.getBoardListWithSearch(params),
                HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "게시글 작성", notes = "제목, 글 내용 request를 받아 게시글을 작성할 수 있습니다. 작성자(닉네임)은 로그인한 유저 정보를 가져옵니다.")
    public ResponseEntity<String> write(@RequestBody CreateQnaBoardRequest request) {
        qnaBoardService.write(request.toViewDto());
        return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 해당하는 게시글 상세 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public ResponseEntity<QnaBoardView> detail(@PathVariable int id) {
        return new ResponseEntity<QnaBoardView>(qnaBoardService.readBoardView(id), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글 id에 해당하는 게시글을 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody QnaBoardView board) {
        qnaBoardService.modify(board);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 id에 해당하는 게시글을 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (qnaBoardService.remove(id)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
}
