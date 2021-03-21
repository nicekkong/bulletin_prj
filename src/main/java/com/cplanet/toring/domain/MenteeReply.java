package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter @Setter
@Accessors(chain = true)
@Entity
@Table(name = "tr_mentee_reply")
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MenteeReply extends AuditEntity {
    private static final long serialVersionUID = -3408052145168838712L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long menteeId;
    private Long memberId;
    private String content;

    @Transient
    private String nickname;
    @Transient
    private String thumbnail;
}
