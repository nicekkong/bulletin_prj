package com.cplanet.toring.dto.response;

import com.cplanet.toring.dto.enums.PostAndReviewType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContentsAndReviewResponseDto {

    @Builder.Default
    private boolean hasNext = false;

    @Builder.Default
    private List<PostList> postLists = new ArrayList<>();

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    public static class PostList {
        private Long id;
        private PostAndReviewType type;
        private String contents;
        private String createDate;
    }



}
