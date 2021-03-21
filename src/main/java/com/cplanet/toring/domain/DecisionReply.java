package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tr_decision_reply")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString
public class DecisionReply extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String comment;
    private String nickname;
    private String thumbnail;
    private Long boardId;
}
