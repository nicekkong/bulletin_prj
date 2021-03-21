package com.cplanet.toring.controller;

import com.cplanet.toring.domain.DecisionBoard;
import com.cplanet.toring.domain.DecisionReply;
import com.cplanet.toring.dto.request.DecisionWriteDto;
import com.cplanet.toring.dto.response.ApiCommonResponse;
import com.cplanet.toring.dto.response.DecisionDetailResponseDto;
import com.cplanet.toring.dto.response.DecisionMainResponseDto;
import com.cplanet.toring.service.DecisionBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class DecisionBoardController extends BaseController {

    final private DecisionBoardService decisionBoardService;

    public DecisionBoardController(DecisionBoardService decisionBoardService) {
        this.decisionBoardService = decisionBoardService;
    }

    /**
     * 결정장애 메인 페이지 목록 조회
     * @param page
     * @return
     */
    @GetMapping(value="/decision/main")
    public ResponseEntity<?> getDecisionBoardMain(@RequestParam(required = false, defaultValue = "0") int page) {
        DecisionMainResponseDto board = decisionBoardService.getDecisionBoardMain(page);
        return ResponseEntity.ok(board);
    }

    /**
     * 결정장애 상세 페이지 내용 조회
     * @param id
     * @return
     */
    @GetMapping(value="/decision/detail")
    public ResponseEntity<?> getDecisionBoardDetail(@RequestParam(name="decision_id") long id) {

        Long memberId = this.getMemberId();

        DecisionDetailResponseDto dto = decisionBoardService.getDecisionBoardDetail(id, memberId);
        return ResponseEntity.ok(dto);
    }

    /**
     * 결정장애 상세 페이지 댓글 조회
     * @param decisionId
     * @return
     */
    @GetMapping(value="/decision/detail/reply")
    public ResponseEntity<?> getDecsionBoardDetailReply(@RequestParam(name="decision_id")long decisionId) {
        List<DecisionReply> replies = decisionBoardService.getDecisionBoardReplies(decisionId);
        return ResponseEntity.ok(replies);
    }

    /**
     * 결정장애 게시글 작성
     * @param dto
     * @return
     */
    @PostMapping(value="/decision/write")
    public ResponseEntity<?> writeDecision(@RequestBody DecisionWriteDto dto) {

        Long memberId = this.getMemberInfo().getId();
        dto.setMemberId(memberId);

        DecisionBoard result = decisionBoardService.writeDecisionBoard(dto);
        return new ResponseEntity(ApiCommonResponse.builder().build(), HttpStatus.OK);
    }

    @PostMapping(value = "/decision/choice")
//    public ResponseEntity<?> choiceDecision(@RequestParam(name="decision_id") Long decisionId
//                                            ,@RequestParam(name="choice_id") Long choiceId) {
    public ResponseEntity<?> choiceDecision(@RequestBody Map<String, Object> params) {
        Long boardId = new Long((Integer) params.get("decision_id"));
        Long choiceId = new Long((Integer) params.get("choice_id"));

        decisionBoardService.selectDecisionChoice(boardId, this.getMemberId(), choiceId);
        return new ResponseEntity(ApiCommonResponse.builder().build(), HttpStatus.OK);
    }


    @PostMapping(value="/decision/reply/write")
    public ResponseEntity<?> writeDecisionReply(@RequestBody Map<String, Object> params) {
        String comment = (String)params.get("comment");
        Long boardId = new Long((String) params.get("decision_id"));

        DecisionReply reply = DecisionReply.builder()
                .comment(comment)
                .nickname(this.getMemberInfo().getProfile().getNickname())
                .boardId(boardId)
                .memberId(this.getMemberId())
                .thumbnail(this.getMemberInfo().getProfile().getThumbnail())
                .build();
        DecisionReply savedReply = decisionBoardService.writeDecisionReply(reply);

        return new ResponseEntity(ApiCommonResponse.builder().build(), HttpStatus.OK);
    }

}
