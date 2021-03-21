package com.cplanet.toring.repository;

import com.cplanet.toring.domain.ContentsReview;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsReviewRepository extends JpaRepository<ContentsReview, Long> {

    Page<ContentsReview> findAllByMemberIdOrderByCreateDateDesc(Long memberId, Pageable pageable);

    @Cacheable(value = "oneMinCache", key="'contentReviewCount_'.concat(#memberId)")
    int countByMemberId(Long memberId);
}
