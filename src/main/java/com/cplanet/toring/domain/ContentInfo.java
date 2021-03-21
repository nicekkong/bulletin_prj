package com.cplanet.toring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ContentInfo {
    private Long id;
    private Long memberid;
    private String title;
    private String keyword;
    private Long categorysub1;
    private Long categorysub2;
    private String thumbimage;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;
    private Float toring;
    private String status;
    private String nickname;
}
