package com.cplanet.toring.controller;

import com.cplanet.toring.domain.ContentInfo;
import com.cplanet.toring.domain.Mentee;
import com.cplanet.toring.domain.MenteeReply;
import com.cplanet.toring.domain.MenteeReview;
import com.cplanet.toring.domain.MenteeUser;
import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.exception.UnauthorizedException;
import com.cplanet.toring.service.MenteeService;
import com.cplanet.toring.service.MentoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"/api/mentee"})
public class MenteeController extends BaseController {

    private MenteeService menteeService;
    private MentoringService mentoringService;

    public MenteeController(MenteeService menteeService, MentoringService mentoringService) {
        this.menteeService = menteeService;
        this.mentoringService = mentoringService;
    }

    @GetMapping(value = "list")
    public List<MenteeUser> getMenteeList(@RequestParam(value = "keyword") String keyword,
                                          @RequestParam(value = "pageNo") Long pageNo) {
        String searchKeyword = StringUtils.isEmpty(keyword)? "" : "%"+keyword+"%";
        return menteeService.getMenteeList(searchKeyword, pageNo);
    }

    @GetMapping(value = "detail")
    public MenteeUser getMenteeDetail(@RequestParam(value = "id") Long id) {
        return menteeService.getMenteeDetail(id);
    }

    @PostMapping(value = "save")
    public ApiResponse saveMentee(@RequestBody MenteeUser mentee) {
        mentee.setMemberid(this.getMemberInfo().getId());
        if(StringUtils.isEmpty(mentee.getMemberid())) {
            throw new UnauthorizedException("mentee/save > memberId is not recognized");
        }
        return menteeService.saveMentee(mentee);
    }

    @PostMapping(value = "update")
    public ApiResponse updateMentee(@RequestBody MenteeUser mentee) {
        return menteeService.updateMentee(mentee);
    }

    @GetMapping(value = "delete")
    public ApiResponse deleteMentee(@RequestParam(value = "id") Long id) {
        return menteeService.deleteMentee(id, this.getMemberInfo().getId());
    }

    @GetMapping(value = "reply/list")
    public List<MenteeReply> getMenteeReplyList(@RequestParam(value = "id") Long menteeId,
                                           @RequestParam(value = "pageNo") Long pageNo) {
        return menteeService.getMenteeReplyList(menteeId, pageNo);
    }

    @PostMapping(value = "reply/save")
    public ApiResponse saveMenteeReply(@RequestBody MenteeReply menteeReply) {
        if(StringUtils.isEmpty(menteeReply.getMemberId())) {
            throw new UnauthorizedException("mentee/save > memberId is not recognized");
        }
        return menteeService.saveMenteeReply(menteeReply);
    }

    @PostMapping(value = "reply/update")
    public ApiResponse updateMenteeReply(@RequestBody MenteeReply menteeReply) {
        return menteeService.updateMenteeReply(menteeReply);
    }

    @GetMapping(value = "reply/delete")
    public ApiResponse deleteMenteeReply(@RequestParam(value = "id") Long id) {
        return menteeService.deleteMenteeReply(id);
    }

    @GetMapping(value = "review/list")
    public List<MenteeReview> getMenteeReviewList(@RequestParam(value = "id") Long contentId,
                                                  @RequestParam(value = "pageNo") Long pageNo) {
        return menteeService.getMenteeReviewList(contentId, pageNo);
    }

    @PostMapping(value = "review/save")
    public ApiResponse saveMenteeReview(@RequestBody MenteeReview menteeReview) {
        if(StringUtils.isEmpty(menteeReview.getMemberid())) {
            throw new UnauthorizedException("mentee/review/save > memberId is not recognized");
        }
        return menteeService.saveMenteeReview(menteeReview);
    }

    @GetMapping(value = "review/delete")
    public ApiResponse deleteMenteeReview(@RequestParam(value = "id") Long id) {
        return menteeService.deleteMenteeReview(id);
    }

    @GetMapping(value = "content/list")
    public List<ContentInfo> getContentList(@RequestParam(value = "sub1", defaultValue = "0") Integer sub1,
                                            @RequestParam(value = "sub2", defaultValue = "0") Integer sub2,
                                            @RequestParam(value = "keyword") String keyword,
                                            @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                            @RequestParam(value = "pageCount", defaultValue = "24") int pageCount,
                                            @RequestParam(value = "type", defaultValue = "new") String type) {
        return mentoringService.getContentListByKeyword(sub1, sub2, keyword, pageNo, pageCount, type);
    }
}
