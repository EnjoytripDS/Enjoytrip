package com.ssafy.enjoytrip.qnaboard.controller;

import com.ssafy.enjoytrip.qnaboard.dto.QnaBoardView;
import com.ssafy.enjoytrip.qnaboard.dto.request.CreateQnaBoardRequest;
import com.ssafy.enjoytrip.qnaboard.service.QnaBoardService;
import java.util.HashMap;
import java.util.List;
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
public class QnaBoardController {

    private static final String SUCCESS = "succes";
    private static final String FAIL = "fail";

    private final QnaBoardService qnaBoardService;


    public QnaBoardController(QnaBoardService qnaBoardService) {
        this.qnaBoardService = qnaBoardService;
    }

    @GetMapping
    public ResponseEntity<List<QnaBoardView>> list() {
        return new ResponseEntity<List<QnaBoardView>>(qnaBoardService.getBoardList(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<QnaBoardView>> search(
            @RequestParam(defaultValue = "") String mode,
            @RequestParam(defaultValue = "") String keyword
    ) {
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("mode", mode);
        params.put("keyword", keyword);

        return new ResponseEntity<List<QnaBoardView>>(qnaBoardService.getBoardListWithSearch(params),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> write(@RequestBody CreateQnaBoardRequest request) {
        qnaBoardService.write(request.toViewDto());
        return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaBoardView> detail(@PathVariable int id) {
        return new ResponseEntity<QnaBoardView>(qnaBoardService.readBoardView(id), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody QnaBoardView board) {
        qnaBoardService.modify(board);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (qnaBoardService.remove(id)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
}
