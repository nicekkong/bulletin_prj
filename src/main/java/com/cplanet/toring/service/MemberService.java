package com.cplanet.toring.service;

import com.cplanet.toring.domain.ContentInfo;
import com.cplanet.toring.domain.Contents;
import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.enums.ContentsStatus;
import com.cplanet.toring.dto.ProfileDto;
import com.cplanet.toring.mapper.MemberMapper;
import com.cplanet.toring.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    final private PasswordEncoder passwordEncoder;
    final private MemberRepository memberRepository;
    final private MentoringService mentoringService;
    final private ContentsRepository contentsRepository;
    final private ContentsReviewRepository contentsReviewRepository;
    final private DecisionBoardRepository decisionBoardRepository;
    final private DecisionReplyRepository decisionReplyRepository;
    final private MenteeRepository menteeRepository;
    final private MenteeReplyRepository menteeReplyRepository;

    final private MemberMapper memberMapper;
    final private ModelMapper modelMapper;

    public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, MentoringService mentoringService,
                         ContentsRepository contentsRepository, ContentsReviewRepository contentsReviewRepository,
                         DecisionBoardRepository decisionBoardRepository, DecisionReplyRepository decisionReplyRepository,
                         MenteeRepository menteeRepository, MenteeReplyRepository menteeReplyRepository,
                         MemberMapper memberMapper, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.mentoringService = mentoringService;
        this.contentsRepository = contentsRepository;
        this.contentsReviewRepository = contentsReviewRepository;
        this.decisionBoardRepository = decisionBoardRepository;
        this.decisionReplyRepository = decisionReplyRepository;
        this.menteeRepository = menteeRepository;
        this.menteeReplyRepository = menteeReplyRepository;
        this.memberMapper = memberMapper;
        this.modelMapper = modelMapper;
    }

    final static int PAGE_SIZE = 4;

    public Member createMember(Member member) {
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member);
    }

    public ProfileDto getMemberProfile(Long myId, Long mentorId) {
        ProfileDto profile = memberMapper.selectMemberProfile(mentorId);
        List<ContentInfo> contents = new ArrayList<>();

        Page<Contents> myContents =  contentsRepository.findByMemberIdOrderByCreateDate(mentorId, PageRequest.of(0, PAGE_SIZE));

        myContents.forEach(c -> {
            ContentInfo tempContents = new ContentInfo();
            modelMapper.map(c, tempContents);
            contents.add(tempContents);
        });
        profile.setHasNextContents(myContents.hasNext());

        if(myId != null) {
            profile.setSubsyn(memberMapper.selectSubscribeYn(myId, mentorId));
        } else {
            profile.setSubsyn("N");
        }
        if(contents != null && contents.size() > 0) {
            profile.setContents(contents);
        }

        profile.setPostCounts(countAllMyPost(mentorId));

        profile.setContentsReplyCount(countMyContentsReview(mentorId));
        profile.setDecisionCount(countMyDecisionBoard(mentorId));
        profile.setDecisionReplyCount(countMyDecisionReply(mentorId));
        profile.setMenteeCount(countMyMentee(mentorId));
        profile.setMenteeReplyCount(countMyMenteeReply(mentorId));

        return profile;
    }

    public boolean saveProfile(ProfileDto profile) {
        boolean result = false;
        if(memberMapper.selectMemberProfile(profile.getMemberid()) != null) {
            result = memberMapper.updateMemberProfile(profile) > 0;
        } else {
            result = memberMapper.insertMemberProfile(profile) > 0;
        }
        return result;
    }

    public List<ProfileDto> getMemberProfileList(String keyword) {
        List<ProfileDto> profileList = memberMapper.selectMemberProfileList(keyword);
        return profileList;
    }

    public boolean requestSubs(Long memberId, Long mentorId, String subsYn) {
        boolean result = false;
        if("Y".equals(subsYn)) {
            result = memberMapper.insertSubscribe(memberId, mentorId) > 0;
        } else {
            result = memberMapper.deleteSubscribe(memberId, mentorId) > 0;
        }
        return result;
    }

    public String getSubsYn(long memberId, long mentorId) {
        return memberMapper.selectSubscribeYn(memberId, mentorId);
    }

    public Member getMemberInfo(Long memberId) {
        Optional<Member> optMember = memberRepository.findById(memberId);
        return optMember.orElse(null);
    }

    /**
     * 내가 쓴 글 전체 갯수
     * @param memberId
     * @return
     */
    private int countAllMyPost(Long memberId) {
        int totalPost = countMyMenteeReply(memberId) + countMyDecisionBoard(memberId) + countMyDecisionReply(memberId)
                + countMyMentee(memberId) + countMyContentsReview(memberId);
        return totalPost;
    }


    /**
     * 사용자 결정장애 게시글 수
     * @param memberId
     * @return
     */
    private int countMyDecisionBoard(Long memberId) {
        return decisionBoardRepository.countByMemberIdAndDisplayStatus(memberId, ContentsStatus.OK);
    }

    /**
     * 결정장애 댓글 갯수
     * @param memberId
     * @return
     */
    private int countMyDecisionReply(Long memberId) {
        return decisionReplyRepository.countByMemberId(memberId);
    }

    /**
     * 멘티공간 작성 글 수
     * @param memberId
     * @return
     */
    private int countMyMentee(Long memberId) {
        return menteeRepository.countByMemberId(memberId);
    }

    /**
     * 멘티공간 댓글 갯수
     * @param memberId
     * @return
     */
    private int countMyMenteeReply(Long memberId) {
        return menteeReplyRepository.countByMemberId(memberId);
    }

    /**
     * 컨텐츠 댓글 갯수
     * @param memberId
     * @return
     */
    private int countMyContentsReview(Long memberId) {
        return contentsReviewRepository.countByMemberId(memberId);
    }
}
