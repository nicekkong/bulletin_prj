package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import com.cplanet.toring.domain.enums.ContentsStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tr_faq_category")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FaqCategory extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @Builder.Default
    private int ordering = 50;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ContentsStatus displayStatus = ContentsStatus.OK;

    @OneToMany(mappedBy = "faqCategory",
            fetch = FetchType.LAZY,
            orphanRemoval=true)
    @Builder.Default
    List<FaqInfo> faqInfos = new ArrayList<>();

    public void addFaqInfo(FaqInfo faqInfo) {
        this.faqInfos.add(faqInfo);
        if(faqInfo.getFaqCategory() != this) {
            faqInfo.setFaqCategory(this);
        }
    }
}
