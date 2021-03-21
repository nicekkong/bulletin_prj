package com.cplanet.toring.dto;

import com.cplanet.toring.domain.ContentInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private Long memberid;
    private String thumbnail;
    private String mentortitle;
    private String nickname;
    private String category;
    private String introduce;
    private List<ContentInfo> contents;
    private boolean hasNextContents;
    private String subsyn;  // 구독자 보유 여부
    private int subscount;  // 구독자수
    private int mentorcount; //구독중인 멘토
    private int purchasecount; // 콘텐츠 열람 내역
    private int postCounts; // 내가 쓴 글 전체 카운트

    private int contentsReplyCount;
    private int menteeCount;
    private int menteeReplyCount;
    private int decisionCount;
    private int decisionReplyCount;
}
