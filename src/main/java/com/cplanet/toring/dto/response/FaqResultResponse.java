package com.cplanet.toring.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FaqResultResponse {

    private List<FaqList> faqLists;
    @Builder.Default
    private boolean hasNext = false;
    @Builder.Default
    private int page = 0;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    private static class FaqList {
        private String categoryCode;
        private String categoryName;
        private List<Faq> faqs;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    private static class Faq {
        private Long id;
        private String question;
        private String answer;

    }

}
