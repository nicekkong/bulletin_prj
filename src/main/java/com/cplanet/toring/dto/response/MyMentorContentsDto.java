package com.cplanet.toring.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MyMentorContentsDto {

    @Builder.Default
    boolean hasNext = false;
    @Builder.Default
    private List<MyMentor> myMentors = new ArrayList<>();


    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class MyMentor {

        private Long mentorId;
        private String thumbnail;
        private String  nickname;
        private String mentorTitle;
        @Builder.Default
        private List<MentorContents> mentorContents = new ArrayList<>();
    }


    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class MentorContents {

        private Long contentsId;
        private String title;
        private boolean isNew;
    }

}
