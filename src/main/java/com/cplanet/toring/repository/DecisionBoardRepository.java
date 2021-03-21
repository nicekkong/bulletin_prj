package com.cplanet.toring.repository;

import com.cplanet.toring.domain.DecisionBoard;
import com.cplanet.toring.domain.enums.ContentsStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DecisionBoardRepository extends JpaRepository<DecisionBoard, Long> {

    // @Query 와 Page 객체를 함께 사용하려면 'countQuery' 속성을 함께 정의해야한다.
    @Query(value = "SELECT b FROM DecisionBoard b JOIN FETCH b.decisionChoices WHERE b.displayStatus='OK' ORDER BY b.createDate desc",
            countQuery = "SELECT COUNT(b) FROM DecisionBoard b WHERE b.displayStatus='OK'")
    Page<DecisionBoard> findDecisionBoardMain(Pageable page);

    @Query("SELECT b FROM DecisionBoard b JOIN FETCH b.decisionChoices WHERE b.displayStatus='OK' AND b.id = :id")
    Optional<DecisionBoard> findByIdAndAndDisplayStatus_Ok(Long id);

    Page<DecisionBoard> findAllByMemberIdAndDisplayStatusOrderByCreateDateDesc(Long memberId, ContentsStatus status, Pageable page);

//    DecisionBoard findAllByDisplayStatus_
// "'mbr_preference_'.concat(#mbrId)"
    @Cacheable(value = "oneMinCache", key="'decisionCount_'.concat(#memberId)")
    int countByMemberIdAndDisplayStatus(Long memberId, ContentsStatus status );

}
