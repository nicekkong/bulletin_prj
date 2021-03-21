package com.cplanet.toring.repository;

import com.cplanet.toring.domain.DecisionDupCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecisionDupCheckRepository extends JpaRepository<DecisionDupCheck, Long> {

    boolean existsByBoardIdAndMemberId(Long boardId, Long memberId);
}
