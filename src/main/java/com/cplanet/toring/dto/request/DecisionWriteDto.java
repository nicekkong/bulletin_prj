package com.cplanet.toring.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DecisionWriteDto {

    private Long memberId;
    private String title;
    private String contents;
    @Builder.Default
    private List<String> options = new ArrayList<>();
    @Builder.Default
    private Long decisionId = null;
}
