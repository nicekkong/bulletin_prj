package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tr_contents")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Contents extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false, insertable = false, updatable = false)
    private Member member;

    private String title;
    private String keyword;
    private Integer categorySub1;
    private Integer categorySub2;
    private String preview;
    private String content;
    private String contentTts;
    private String attatchMeta;
    private String thumbImage;
    private String backgroundImage;
    @Builder.Default
    private Float toring = 0.0f;
    @Builder.Default
    private String status = "temporary";
    @Builder.Default
    private LocalDateTime viewDate = LocalDateTime.now();

//    @OneToMany
//    @Builder.Default
//    private List<ContentsReview> reviews = new ArrayList<>();

    @Override
    public String toString() {
        return "Contents{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
