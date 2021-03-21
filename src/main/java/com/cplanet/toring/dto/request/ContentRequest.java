package com.cplanet.toring.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ContentRequest {
    private Long id;
    private Long memberid;
    private String title;
    private String keyword;
    private Long categorysub1;
    private Long categorysub2;
    private String preview;
    private String content;
    private String content2;
    private String content3;
    private String contenttts;
    private String attatchmeta;
    private String thumbimage;
    private String backgroundimage;
    private Float toring;
    private String status;
    private LocalDateTime viewdate;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;
    private String requesttype;
    private Long pageno;
}
