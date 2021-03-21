package com.cplanet.toring.mapper;

import com.cplanet.toring.domain.Mentee;
import com.cplanet.toring.domain.MenteeReply;
import com.cplanet.toring.domain.MenteeReview;
import com.cplanet.toring.domain.MenteeUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MenteeMapper {

    MenteeUser selectMenteeDetail(long id);

    List<MenteeUser> selectMenteeList(Map<String, Object> param);

    int insertMentee(MenteeUser mentee);

    int updateMentee(MenteeUser mentee);

    int deleteMentee(Long id, Long memberId);

    int insertMenteeReply(MenteeReply menteeReply);

    int updateMenteeReply(MenteeReply menteeReply);

    int deleteMenteeReply(Long id);

    List<MenteeReply> selectMenteeReplyList(Map<String, Object> param);

    List<MenteeReview> selectMenteeReviewList(Map<String, Object> param);

    int insertMenteeReview(MenteeReview menteeReview);

    int updateMenteeReview(MenteeReview menteeReview);

    int deleteMenteeReview(Long id);
}
