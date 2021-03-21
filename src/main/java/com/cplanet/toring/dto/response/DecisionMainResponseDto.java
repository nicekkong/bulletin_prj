package com.cplanet.toring.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DecisionMainResponseDto {

    private Integer count;
    private Boolean hasNext;
    @Builder.Default
    private List<DecisionMain> decisionMains = new ArrayList<>();

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    public static class DecisionMain {
        private Long id;
        private String title;
        @Builder.Default
        private List<String> optionText = new ArrayList<>();

    }

}
