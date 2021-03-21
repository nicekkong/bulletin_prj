package com.cplanet.toring.service;

import com.cplanet.toring.domain.Mentee;
import com.cplanet.toring.domain.MenteeReply;
import com.cplanet.toring.domain.*;
import com.cplanet.toring.dto.ApiResponse;
import com.cplanet.toring.mapper.MenteeMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenteeService {

    final private MenteeMapper menteeMapper;

    public MenteeService(MenteeMapper menteeMapper) {
        this.menteeMapper = menteeMapper;
    }

    public MenteeUser getMenteeDetail(Long id) {
        return menteeMapper.selectMenteeDetail(id);
    }

    public List<MenteeUser> getMenteeList(String keyword, Long pageNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("start", getStartNo(pageNo, 6));
        param.put("pagecount", 6);
        return menteeMapper.selectMenteeList(param);
    }

    public ApiResponse saveMentee(MenteeUser mentee) {
        String type = mentee.getId() == null ? "저장" : "수정";
        boolean result = false;
        if ("저장".equals(type)) {
            result = menteeMapper.insertMentee(mentee) > 0? true : false;
        } else {
            result = menteeMapper.updateMentee(mentee) > 0? true : false;
        }
        return new ApiResponse(result, result? type + " 완료" : type +" 실패");
    }

    public ApiResponse updateMentee(MenteeUser mentee) {
        boolean result = menteeMapper.updateMentee(mentee) > 0? true : false;
        return new ApiResponse(result, result? "수정 완료" : "수정 실패");
    }

    public ApiResponse deleteMentee(Long id, Long memberId) {
        boolean result = menteeMapper.deleteMentee(id, memberId) > 0? true : false;
        return new ApiResponse(result, result? "삭제 완료" : "삭제 실패");
    }

    public ApiResponse saveMenteeReply(MenteeReply menteeReply) {
        String type = menteeReply.getId() == null ? "저장" : "수정";
        boolean result = false;
        if ("저장".equals(type)) {
            result = menteeMapper.insertMenteeReply(menteeReply) > 0? true : false;
        } else {
            result = menteeMapper.updateMenteeReply(menteeReply) > 0? true : false;
        }
        return new ApiResponse(result, result? type + " 완료" : type +" 실패");
    }

    public ApiResponse updateMenteeReply(MenteeReply menteeReply) {
        boolean result = menteeMapper.updateMenteeReply(menteeReply) > 0? true : false;
        return new ApiResponse(result, result? "수정 완료" : "수정 실패");
    }

    public ApiResponse deleteMenteeReply(Long id) {
        boolean result = menteeMapper.deleteMenteeReply(id) > 0? true : false;
        return new ApiResponse(result, result? "삭제 완료" : "삭제 실패");
    }

    public List<MenteeReply> getMenteeReplyList(Long menteeId, Long pageNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("menteeid", menteeId);
        param.put("start", getStartNo(pageNo, 2));
        param.put("pagecount", 2);
        return menteeMapper.selectMenteeReplyList(param);
    }

    private Long getStartNo(Long pageNo, int pageCount) {
        return (pageNo-1) * pageCount;
    }

    public List<MenteeReview> getMenteeReviewList(Long contentId, Long pageNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("contentId", contentId);
        param.put("start", getStartNo(pageNo, 2));
        param.put("pagecount", 2);
        return menteeMapper.selectMenteeReviewList(param);
    }

    public ApiResponse saveMenteeReview(MenteeReview menteeReview) {
        String type = menteeReview.getId() == null ? "저장" : "수정";
        boolean result = false;
        if ("저장".equals(type)) {
            result = menteeMapper.insertMenteeReview(menteeReview) > 0? true : false;
        } else {
            result = menteeMapper.updateMenteeReview(menteeReview) > 0? true : false;
        }
        return new ApiResponse(result, result? type + " 완료" : type +" 실패");
    }

    public ApiResponse deleteMenteeReview(Long id) {
        boolean result = menteeMapper.deleteMenteeReview(id) > 0? true : false;
        return new ApiResponse(result, result? "삭제 완료" : "삭제 실패");
    }
}
