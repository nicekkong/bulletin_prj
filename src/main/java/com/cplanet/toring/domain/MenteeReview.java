package com.cplanet.toring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class MenteeReview {
    private Long id;
    private Long contentid;
    private Long memberid;
    private String content;
    private Date createdate;
    private Date updatedate;
    private String nickname;
    private String thumbnail;
}
