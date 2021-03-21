package com.cplanet.toring.repository;

import com.cplanet.toring.domain.Mentee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    @Cacheable(value = "oneMinCache", key="'menteeReviewCount_'.concat(#memberId)")
    int countByMemberId(Long memberId);
}
