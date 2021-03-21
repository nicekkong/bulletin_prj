package com.cplanet.toring.controller;

import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.dto.ProfileDto;
import com.cplanet.toring.dto.response.ContentsAndReviewResponseDto;
import com.cplanet.toring.dto.response.MyMentorContentsDto;
import com.cplanet.toring.service.MemberService;
import com.cplanet.toring.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = {"/api/my"})
public class MyController extends BaseController {
    final private MemberService memberService;
    final private MyService myService;

    public MyController(MemberService memberService, MyService myService) {
        this.memberService = memberService;
        this.myService = myService;
    }

    @GetMapping(value = "profile")
    public ProfileDto getMyProfile() {
        ProfileDto response = null;
        long memberId = this.getMemberInfo().getId();
        response = memberService.getMemberProfile(memberId, memberId);
        return response;
    }

    @PostMapping(value ="profile/save")
    public ApiResponse saveMyProfile(@RequestBody ProfileDto profile) {
        profile.setMemberid(this.getMemberInfo().getId());
        boolean result = memberService.saveProfile(profile);
        String message = "success";
        if(!result) {
            message = "fail";
        }
        return new ApiResponse(result, message);
    }

    @GetMapping(value="/contents")
    public ResponseEntity<?> getMyContents(@RequestParam(value="page", defaultValue = "0")int page){
        return ResponseEntity.ok(myService.getMyContents(this.getMemberId(), page));
    }


    @GetMapping(value="/posts")
    public ResponseEntity getMyAllHistories(@RequestParam(value="type") String type,
                                            @RequestParam(value="page",required = false, defaultValue = "0")int page) {

        Long memberId = this.getMemberId();
        ContentsAndReviewResponseDto result =  myService.getMyPostAndReviewsByType(memberId, type, page);

        return ResponseEntity.ok(result);

    }

    @GetMapping(value = "/mentors")
    public ResponseEntity<?> getMyMentors(@RequestParam(value = "page", required = false, defaultValue = "0")int page) {

        Long memberId = this.getMemberId();
        MyMentorContentsDto myMentorContents = myService.getMyMentorContentsList(memberId, page);

        return ResponseEntity.ok(myMentorContents);
    }
}
