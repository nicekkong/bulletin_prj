package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Accessors(chain = true)
@Entity
@Table(name = "tr_mentees")
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Mentee extends AuditEntity {

    private static final long serialVersionUID = 9184767596646446619L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String title;
    private String keyword;
    private String content;

    @Transient
    private String nickname;
}
