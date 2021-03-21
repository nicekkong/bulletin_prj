package com.cplanet.toring.repository;

import com.cplanet.toring.domain.DecisionReply;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecisionReplyRepository extends JpaRepository<DecisionReply, Long> {

    List<DecisionReply> findDecisionRepliesByBoardIdOrderByCreateDate(Long boardId);

    List<DecisionReply> findDecisionRepliesByBoardId(Long boardId);

    Page<DecisionReply> findDecisionReplyByMemberIdOrderByCreateDateDesc(Long memberId, Pageable pageable);

    @Cacheable(value = "oneMinCache", key="'decisionReviewCount_'.concat(#memberId)")
    int countByMemberId(Long memberId);
}
