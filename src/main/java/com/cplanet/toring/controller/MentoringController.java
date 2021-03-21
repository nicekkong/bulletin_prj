package com.cplanet.toring.controller;

import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.dto.CategoryDto;
import com.cplanet.toring.dto.ProfileDto;
import com.cplanet.toring.dto.request.ContentRequest;
import com.cplanet.toring.dto.request.SubscribeRequest;
import com.cplanet.toring.dto.response.ContentResponse;
import com.cplanet.toring.exception.UnauthorizedException;
import com.cplanet.toring.service.MemberService;
import com.cplanet.toring.service.MentoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"/api/mentoring"})
public class MentoringController extends BaseController {

    private MentoringService mentoringService;
    private MemberService memberService;

    public MentoringController(MentoringService mentoringService, MemberService memberService) {
        this.mentoringService = mentoringService;
        this.memberService = memberService;
    }

    @GetMapping(value = "content/info")
    public ContentResponse getContent(@RequestParam(value = "contentid") long contentid) {
        return mentoringService.getContentInfo(contentid);
    }

    @PostMapping(value = "content/save")
    public ContentResponse contentSave(@RequestBody ContentRequest content) {
        content.setMemberid(this.getMemberInfo().getId());
        if(StringUtils.isEmpty(content.getMemberid())) {
            throw new UnauthorizedException("content/save > memberId is not recognized");
        }
        return mentoringService.registerContent(content);
    }

    @GetMapping(value = "content/category")
    public ResponseEntity<CategoryDto> getCategory() {
        CategoryDto response = new CategoryDto();
        response.setCategory(mentoringService.getToringCategories());
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "profile")
    public ProfileDto getMentorProfile(@RequestParam(value = "memberid") long memberId) {
        Long myId = this.getMemberInfo().getId();
        ProfileDto response;
        response = memberService.getMemberProfile(myId, memberId);
        return response;
    }

    @GetMapping(value = "profile/list")
    public List<ProfileDto> getProfiles(@RequestParam(value = "keyword") String keyword) {
        List<ProfileDto> response;
        String searchKeyword = StringUtils.isEmpty(keyword)? "" : "%"+keyword+"%";
        response = memberService.getMemberProfileList(searchKeyword);
        return response;
    }

    @PostMapping(value = "subs/request")
    public ApiResponse requestSubs(@RequestBody SubscribeRequest request) {
        Long memberId = this.getMemberInfo().getId();
        boolean result = memberService.requestSubs(memberId, request.getMentorid(), request.getSubsyn());
        String message = "구독요청 중 오류가 발생했습니다.";
        if (result) {
            message = "Y".equals(request.getSubsyn())? "구독요청이 완료되었습니다.":"구독취소가 처리되었습니다.";
        }

        return new ApiResponse(result, message);
    }
}
