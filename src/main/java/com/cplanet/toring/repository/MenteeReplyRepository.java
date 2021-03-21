package com.cplanet.toring.repository;

import com.cplanet.toring.domain.MenteeReply;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeReplyRepository extends JpaRepository<MenteeReply, Long> {

    @Cacheable(value = "oneMinCache", key="'menteeReviewCount_'.concat(#memberId)")
    int countByMemberId(Long memberId);
}
