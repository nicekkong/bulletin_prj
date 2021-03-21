package com.cplanet.toring.domain;

import com.cplanet.toring.domain.common.AuditEntity;
import com.cplanet.toring.domain.enums.ContentsStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tr_faq_info")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FaqInfo extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private FaqCategory faqCategory;

    private String question;
    private String answer;

    @Builder.Default
    private int ordering = 50;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ContentsStatus displayStatus = ContentsStatus.OK;

    @Transient
    private String categoryName;

//    void setCategoryName(String name) {
//        System.out.println("setCategoryName ::: " + name);
//        this.categoryName = this.faqCategory.getName();
//    }

    public void setFaqCategory(FaqCategory faqCategory) {
        if(this.faqCategory != null) {
            this.faqCategory.getFaqInfos().remove(this);
        }
        this.faqCategory = faqCategory;
        faqCategory.getFaqInfos().add(this);
    }
}
