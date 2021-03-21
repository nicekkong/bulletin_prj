package com.cplanet.toring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ApiCommonResponse {
    @Builder.Default
    private String code = "0000";
    @Builder.Default
    private String message = "Success";
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorMessage;
}
