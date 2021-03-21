package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import com.cplanet.toring.domain.enums.ContentsStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tr_decision_board")
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DecisionBoard extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String title;
    private String contents;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ContentsStatus displayStatus = ContentsStatus.OK;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "decisionBoard",
            cascade = CascadeType.ALL)
    @Builder.Default
    private List<DecisionChoice> decisionChoices = new ArrayList<>();

    public void addDecisionChoice(DecisionChoice decisionChoice) {
        this.decisionChoices.add(decisionChoice);
        if(decisionChoice.getDecisionBoard() != this) {
            decisionChoice.setDecisionBoard(this);
        }
    }
}
