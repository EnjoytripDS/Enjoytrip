package com.ssafy.enjoytrip.qnaboard.controller;

import com.ssafy.enjoytrip.commons.exception.ErrorCode;
import com.ssafy.enjoytrip.commons.jwt.service.JwtService;
import com.ssafy.enjoytrip.commons.response.CommonApiResponse;
import com.ssafy.enjoytrip.qnaboard.dto.BoardComment;
import com.ssafy.enjoytrip.qnaboard.dto.BoardImage;
import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import com.ssafy.enjoytrip.qnaboard.dto.request.CreateBoardCommentRequest;
import com.ssafy.enjoytrip.qnaboard.dto.request.CreateQnaBoardRequest;
import com.ssafy.enjoytrip.qnaboard.exception.ImageNotFoundException;
import com.ssafy.enjoytrip.qnaboard.service.BoardCommentService;
import com.ssafy.enjoytrip.qnaboard.service.BoardImageService;
import com.ssafy.enjoytrip.qnaboard.service.QnaBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("api/v1/qna-board")
@Api(tags = "Q&A 게시판 API")
public class QnaBoardController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final QnaBoardService qnaBoardService;

    private final BoardCommentService boardCommentService;

    private final BoardImageService boardImageService;

    @Autowired
    private JwtService jwtService;

    public QnaBoardController(QnaBoardService qnaBoardService,
            BoardCommentService boardCommentService,
            BoardImageService boardImageService) {
        this.qnaBoardService = qnaBoardService;
        this.boardCommentService = boardCommentService;
        this.boardImageService = boardImageService;
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
    public CommonApiResponse<Integer> write(@RequestBody CreateQnaBoardRequest request,
            HttpServletRequest req) {
        int ret = -1;
        if (jwtService.checkToken(req.getHeader("access-token"))) {
            qnaBoardService.write(request.toViewDto());
            ret = qnaBoardService.getLastId();
        }
        return CommonApiResponse.success(ret);
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

    @GetMapping("/{id}/comment")
    @ApiOperation(value = "게시글 댓글 조회", notes = "게시글 id에 해당하는 게시글의 댓글 목록을 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<List<BoardComment>> listComment(@PathVariable int id,
            HttpServletRequest request) {
        List<BoardComment> result = new ArrayList<>();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            result = boardCommentService.getCommentList(id);
        }
        return CommonApiResponse.success(result);
    }

    @PostMapping("/{id}/comment")
    @ApiOperation(value = "게시글 댓글 작성", notes = "게시글 id에 해당하는 게시글에 댓글을 작성합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<String> writeComment(@PathVariable int id,
            @RequestBody CreateBoardCommentRequest request,
            HttpServletRequest req) {
        BoardComment bc = request.toViewDto();
        bc.setQnaBoardId(id);
        if (jwtService.checkToken(req.getHeader("access-token"))) {
            boardCommentService.write(bc);
        }
        return CommonApiResponse.success("ok");
    }

    @PutMapping("/{id}/comment/{commentId}")
    @ApiOperation(value = "게시글 댓글 수정", notes = "게시글 id에 해당하는 게시글에서 내가 작성한 댓글을 수정할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "commentId", value = "댓글 id", example = "1")
    })
    public CommonApiResponse<String> updateComment(@PathVariable int id,
            @PathVariable int commentId, @RequestBody BoardComment boardComment,
            HttpServletRequest request) {
        boardComment.setQnaBoardId(id);
        boardComment.setId(commentId);
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            boardCommentService.modify(boardComment);
        }
        return CommonApiResponse.success("ok");
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    @ApiOperation(value = "게시글 댓글 삭제", notes = "게시글 id에 해당하는 게시글의 댓글을 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "commentId", value = "댓글 id", example = "1")
    })
    public CommonApiResponse<String> deleteComment(@PathVariable int id,
            @PathVariable int commentId,
            HttpServletRequest request) {
        BoardComment bc = new BoardComment(commentId, id);
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            if (!boardCommentService.remove(bc)) {
                return CommonApiResponse.fail(ErrorCode.QNA_NOT_FOUND);
            }
        }
        return CommonApiResponse.success("ok");
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

    @PostMapping("/{id}/image")
    @ApiOperation(value = "게시글 사진 업로드", notes = "사진 url, 사진 이름을 받아 게시글에 등록할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<String> uploadImage(@PathVariable int id,
            @RequestParam(value = "uploadFile", required = false) List<MultipartFile> multipartFile,
            HttpServletRequest req) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ImageNotFoundException("이미지가 없음");
        }
        if (jwtService.checkToken(req.getHeader("access-token"))) {
            String uploadFilePath = "C:/travelary/image/";
            for (MultipartFile f : multipartFile) {
                String prefix = f.getOriginalFilename()
                        .substring(f.getOriginalFilename().lastIndexOf(".") + 1,
                                f.getOriginalFilename().length());
                String fileName = UUID.randomUUID().toString() + "." + prefix;
                File folder = new File(uploadFilePath);
                if (!folder.isDirectory()) {
                    folder.mkdirs();
                }
                String pathName = uploadFilePath + fileName;
                String resourcePathName = "/image/" + fileName;
                File dest = new File(pathName);
                try {
                    f.transferTo(dest);
                    BoardImage boardImage = new BoardImage();
                    boardImage.setImageType(f.getContentType());
                    boardImage.setImageName(f.getOriginalFilename());
                    boardImage.setFileName(fileName);
                    boardImage.setPathName(pathName);
                    boardImage.setSize((int) f.getSize());
                    boardImage.setImageUrl(resourcePathName);
                    boardImage.setQnaBoardId(id);
                    boardImageService.uploadImage(boardImage);
                } catch (IllegalStateException | IOException e) {
                    log.info("에러 발생 ㅎㅎ");
                    return CommonApiResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
                }
            }
        }
        return CommonApiResponse.success("ok");
    }

    @GetMapping("/{id}/image")
    @ApiOperation(value = "게시글 이미지 조회", notes = "게시글 id에 해당하는 게시글의 이미지 목록을 조회할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<List<BoardImage>> listImage(@PathVariable int id,
            HttpServletRequest request) {
        List<BoardImage> result = new ArrayList<>();
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            result = boardImageService.getImage(id);
        }
        return CommonApiResponse.success(result);
    }

    @DeleteMapping("/{id}/image")
    @ApiOperation(value = "해당 게시글 이미지 전부 삭제", notes = "게시글 id에 해당하는 게시글의 이미지 목록을 삭제할 수 있습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    })
    public CommonApiResponse<String> deleteAllImages(@PathVariable int id,
            HttpServletRequest request) {
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            boardImageService.removeAllImages(id);
        }
        return CommonApiResponse.success("ok");
    }
}
