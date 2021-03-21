package com.cplanet.toring.dto.response;

import com.cplanet.toring.domain.ContentInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContentInfoResponseDto {

    boolean hasNext;
    @Builder.Default
    List<ContentInfo> contents = new ArrayList<>();

}
