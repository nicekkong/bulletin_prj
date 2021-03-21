package com.cplanet.toring.service;

import com.cplanet.toring.domain.*;
import com.cplanet.toring.domain.enums.ContentsStatus;
import com.cplanet.toring.dto.enums.PostAndReviewType;
import com.cplanet.toring.dto.response.ContentInfoResponseDto;
import com.cplanet.toring.dto.response.ContentsAndReviewResponseDto;
import com.cplanet.toring.dto.response.MyMentorContentsDto;
import com.cplanet.toring.repository.*;
import com.cplanet.toring.utils.DateUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class MyService {

    private final ContentsRepository contentsRepository;
    private final ContentsReviewRepository contentsReviewRepository;
    private final DecisionBoardRepository decisionBoardRepository;
    private final DecisionReplyRepository decisionReplyRepository;
    private final MenteeReplyRepository menteeReplyRepository;
    private final MenteeRepository menteeRepository;
    private final MemberService memberService;
    private final MyMentorRepository myMentorRepository;

    private final ModelMapper modelMapper;

    public MyService(ContentsRepository contentsRepository, ContentsReviewRepository contentsReviewRepository,
                     DecisionBoardRepository decisionBoardRepository, DecisionReplyRepository decisionReplyRepository,
                     MenteeReplyRepository menteeReplyRepository, MenteeRepository menteeRepository,
                     ModelMapper modelMapper, MemberService memberService, MyMentorRepository myMentorRepository) {
        this.contentsRepository = contentsRepository;
        this.contentsReviewRepository = contentsReviewRepository;
        this.decisionBoardRepository = decisionBoardRepository;
        this.decisionReplyRepository = decisionReplyRepository;
        this.menteeReplyRepository = menteeReplyRepository;
        this.menteeRepository = menteeRepository;
        this.modelMapper = modelMapper;
        this.memberService = memberService;
        this.myMentorRepository = myMentorRepository;
    }

    private final static Logger logger = LoggerFactory.getLogger(MyService.class);
    private final static int MODAL_PAGE_SIZE = 3;
    private final static int CONTENTS_PAGE_SIZE = 4;
    private final static int MY_MENTOR_PAGE_SIZE = 1;



    public ContentsAndReviewResponseDto getMyPostAndReviewsByType(Long memberId, String postType, int page) {
        ContentsAndReviewResponseDto result = new ContentsAndReviewResponseDto();
        if("all".equals(postType)) {
            result = getMyAllPostAndReviews(memberId, page);
        } else if ("review".equals(postType)) {
            result = getMyAllPostAndReviews(memberId, page);
        } else if("mentee".equals(postType)) {

        } else if("mentee_review".equals(postType)) {

        }else if("decision".equals(postType)) {
            result = getMyDecisionBoards(memberId, page);
        }else if("decision_review".equals(postType)) {
            result = getMyDecisionReviews(memberId, page);
        }else {

        }
        return result;
    }

    /**
     * 내가 쓴 컨텐츠 리뷰
     * @param memberId
     * @param page
     * @return
     */
    public ContentsAndReviewResponseDto getMyAllPostAndReviews(Long memberId, int page) {

        // 컨텐츠 리뷰
        Page<ContentsReview> contentsReviews = contentsReviewRepository.findAllByMemberIdOrderByCreateDateDesc(memberId, PageRequest.of(page, MODAL_PAGE_SIZE));

        List<ContentsAndReviewResponseDto.PostList> postList = new ArrayList<>();
        for(ContentsReview item : contentsReviews.getContent()) {
            postList.add(ContentsAndReviewResponseDto.PostList.builder()
                    .id(item.getContentsId())
                    .contents(item.getContent())
                    .type(PostAndReviewType.CONTENT_REVIEW)
                    .createDate(DateUtils.toHumanizeDateTime(item.getCreateDate()))
                    .build());
        }

        ContentsAndReviewResponseDto results = ContentsAndReviewResponseDto.builder()
                .hasNext(contentsReviews.hasNext())
                .postLists(postList)
                .build();

        return results;
    }


    /**
     * 마이페이지 >> 내가쓴 글 >> 결정장애
     * @param memberId
     * @param page
     * @return
     */
    private ContentsAndReviewResponseDto getMyDecisionBoards(Long memberId, int page) {

        Page<DecisionBoard> decisionBoards = decisionBoardRepository.findAllByMemberIdAndDisplayStatusOrderByCreateDateDesc
                (memberId, ContentsStatus.OK, PageRequest.of(page, MODAL_PAGE_SIZE));

        List<ContentsAndReviewResponseDto.PostList> postList = new ArrayList<>();
        for(DecisionBoard item : decisionBoards.getContent()) {
            postList.add(ContentsAndReviewResponseDto.PostList.builder()
                    .id(item.getId())
                    .contents(item.getTitle())
                    .type(PostAndReviewType.DECISION)
                    .createDate(DateUtils.toHumanizeDateTime(item.getCreateDate()))
                    .build());
        }

        ContentsAndReviewResponseDto results = ContentsAndReviewResponseDto.builder()
                .hasNext(decisionBoards.hasNext())
                .postLists(postList)
                .build();

        return results;
    }

    private ContentsAndReviewResponseDto getMyDecisionReviews(Long memberId, int page) {
        Page<DecisionReply> decisionReplies = decisionReplyRepository.findDecisionReplyByMemberIdOrderByCreateDateDesc(memberId, PageRequest.of(page, MODAL_PAGE_SIZE));

        List<ContentsAndReviewResponseDto.PostList> postList = new ArrayList<>();
        for(DecisionReply item : decisionReplies.getContent()) {
            postList.add(ContentsAndReviewResponseDto.PostList.builder()
                    .id(item.getBoardId())
                    .contents(item.getComment())
                    .type(PostAndReviewType.DECISION_REVIEW)
                    .createDate(DateUtils.toHumanizeDateTime(item.getCreateDate()))
                    .build());
        }

        ContentsAndReviewResponseDto results = ContentsAndReviewResponseDto.builder()
                .hasNext(decisionReplies.hasNext())
                .postLists(postList)
                .build();

        return results;

    }


    private PostAndReviewType convertType(String type) {

        switch(type) {
            case ("all"):
                return PostAndReviewType.ALL;
            case("content_review"):
                return PostAndReviewType.CONTENT_REVIEW;
            case("mentee"):
                return PostAndReviewType.MENTEE;
            case("mentee_review"):
                return PostAndReviewType.MENTEE_REVIEW;
            case("decision"):
                return PostAndReviewType.DECISION;
            case("decision_review"):
                return PostAndReviewType.DECISION_REVIEW;
            default:
                logger.error("PostAndReviewType not matched::: {}", type);
                return PostAndReviewType.ALL;
        }
    }


    public ContentInfoResponseDto getMyContents(Long memberId, int page) {

        Page<Contents> contents = contentsRepository.findByMemberIdOrderByCreateDate(memberId, PageRequest.of(page, CONTENTS_PAGE_SIZE));

        List<ContentInfo> contentsList = new ArrayList<>();
        contents.getContent().forEach(c -> {
            ContentInfo content = new ContentInfo();
            modelMapper.map(c, content);
            contentsList.add(content);
        });

        ContentInfoResponseDto result = ContentInfoResponseDto.builder()
                .contents(contentsList)
                .hasNext(contents.hasNext())
                .build();

        return result;
    }

    // 구독중인 멘토 리스트 최신 여부 ("New")
    private Function<LocalDateTime, Boolean> setIsNew = t -> {
        LocalDateTime now = LocalDateTime.now();
        if(now.minusDays(3).isBefore(t)) {
            return true;
        } else {
            return false;
        }

    };

    /**
     * 마이페이지 >> 구독중인 멘토 리스트
     * @param memberId
     * @param page
     * @return
     */
    public MyMentorContentsDto getMyMentorContentsList(Long memberId, int page) {

        MyMentorContentsDto result = new MyMentorContentsDto();

        // 1. 사용자별 멘토를 먼저 조회한후,
        Page<MyMentor> pagedContents = myMentorRepository.findByMentorByMemberId(memberId, PageRequest.of(page, MY_MENTOR_PAGE_SIZE));


        List<MyMentorContentsDto.MyMentor> mentors = new ArrayList<>();
        pagedContents.getContent().forEach(m -> {
            // 멘토 프로필 추가
            MyMentorContentsDto.MyMentor mentor = MyMentorContentsDto.MyMentor.builder()
                    .mentorId(m.getMentorId())
                    .thumbnail(m.getProfile().getThumbnail())
                    .nickname(m.getProfile().getNickname())
                    .mentorTitle(m.getProfile().getMentorTitle())
                    .build();


            // 2. 각 멘토별 컨텐츠 조회하여 추가(최신순 Max 3개까지만)
            List<Contents> contents = contentsRepository.findTop3ByMemberIdOrderByCreateDateDesc(m.getMentorId());
            contents.forEach(c -> {
                MyMentorContentsDto.MentorContents mentorContents = MyMentorContentsDto.MentorContents.builder()
                        .contentsId(c.getId())
                        .isNew(setIsNew.apply(c.getCreateDate()))
                        .title(c.getTitle())
                        .build();

                mentor.getMentorContents().add(mentorContents);
            });
            mentors.add(mentor);
        });

        result.setHasNext(pagedContents.hasNext());
        result.setMyMentors(mentors);

        return result;
    }


}
