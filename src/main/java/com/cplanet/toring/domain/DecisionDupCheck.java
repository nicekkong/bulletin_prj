package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tr_decision_dup_check")
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DecisionDupCheck extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private Long memberId;
    private Long choiceId;
}
