package com.ssafy.enjoytrip.qnaboard.controller;

import com.ssafy.enjoytrip.commons.exception.ErrorCode;
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
import javax.servlet.http.HttpServlet;
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
    public CommonApiResponse<List<QnaBoardView>> search(
            @RequestParam(defaultValue = "") String mode,
            @RequestParam(defaultValue = "") String keyword,
            HttpServletRequest request
    ) {
        List<QnaBoardView> result = new ArrayList<>();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("mode", mode);
            params.put("keyword", keyword);
            result = qnaBoardService.getBoardListWithSearch(params);
        }
        return CommonApiResponse.success(result);
    }

    @PostMapping
    @ApiOperation(value = "게시글 작성", notes = "제목, 글 내용 request를 받아 게시글을 작성할 수 있습니다. 작성자(닉네임)은 로그인한 유저 정보를 가져옵니다.")
    public CommonApiResponse<String> write(@RequestBody CreateQnaBoardRequest request,
            HttpServletRequest req) {
        if (jwtService.checkToken(req.getHeader("access-token"))) {
            qnaBoardService.write(request.toViewDto());
        }
        return CommonApiResponse.success("ok");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 해당하는 게시글 상세 정보를 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<QnaBoardView> detail(@PathVariable int id,
            HttpServletRequest request) {
        QnaBoardView qbv = new QnaBoardView();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            qbv = qnaBoardService.readBoardView(id);
        }
        return CommonApiResponse.success(qbv);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글 id에 해당하는 게시글을 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<String> update(@PathVariable int id, @RequestBody QnaBoardView board,
            HttpServletRequest request) {
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            qnaBoardService.modify(board);
        }
        return CommonApiResponse.success("ok");
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 id에 해당하는 게시글을 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<String> delete(@PathVariable int id, HttpServletRequest request) {
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            if (!qnaBoardService.remove(id)) {
                return CommonApiResponse.fail(ErrorCode.QNA_NOT_FOUND);
            }
        }
        return CommonApiResponse.success("ok");
    }
}
