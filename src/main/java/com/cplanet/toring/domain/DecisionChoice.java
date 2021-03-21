package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "tr_decision_choice")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DecisionChoice extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText;
    @Builder.Default
    private Integer count = 0;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false) // inner join
    private DecisionBoard decisionBoard;

    public void setDecisionBoard(DecisionBoard decisionBoard) {
        if(this.decisionBoard != null) {
            this.decisionBoard.getDecisionChoices().remove(this);
        }
        this.decisionBoard = decisionBoard;
        decisionBoard.getDecisionChoices().add(this);
    }
}
