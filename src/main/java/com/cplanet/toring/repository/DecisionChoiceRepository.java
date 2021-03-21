package com.cplanet.toring.repository;

import com.cplanet.toring.domain.DecisionChoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DecisionChoiceRepository extends JpaRepository<DecisionChoice, Long> {

    @Modifying
    @Transactional
    @Query(value = "update tr_decision_choice " +
                    "  set count = count +1 " +
                    "where id = :id ", nativeQuery = true)
    void updateDecisionChoiceCount(@Param("id") Long id);

}
