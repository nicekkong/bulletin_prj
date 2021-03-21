package com.cplanet.toring.mapper;

import com.cplanet.toring.dto.ProfileDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {
    ProfileDto selectMemberProfile(long memberId);

    int insertMemberProfile(ProfileDto profile);

    int updateMemberProfile(ProfileDto profile);

    List<ProfileDto> selectMemberProfileList(String keyword);

    int insertSubscribe(Long memberId, Long mentorId);

    int deleteSubscribe(Long memberId, Long mentorId);

    String selectSubscribeYn(long memberId, long mentorId);
}
