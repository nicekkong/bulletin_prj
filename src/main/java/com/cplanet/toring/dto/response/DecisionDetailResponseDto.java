package com.cplanet.toring.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DecisionDetailResponseDto {
    private Long id;
    private String title;
    private String nickName;
    private Long memberId;
    private String createTime;
    private String contents;
    private Long answerId01;
    private String answer01;
    private int count01;
    private Long answerId02;
    private String answer02;
    private int count02;
    @Builder.Default
    private boolean isResponsed = false;

    public boolean getIsResponsed() {
        return isResponsed;
    }

    public void setIsResponsed(boolean responsed) {
        isResponsed = responsed;
    }
}
