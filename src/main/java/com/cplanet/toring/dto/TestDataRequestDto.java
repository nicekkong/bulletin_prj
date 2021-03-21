package com.cplanet.toring.dto;


import com.cplanet.toring.domain.TestData;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class TestDataRequestDto {

    private Integer intData;
    private String stringData;

    public TestData toEntity() {
        return TestData.builder()
                .intData(this.intData)
                .stringData(this.stringData)
                .build();
    }
}
