package com.cplanet.toring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class MenteeUser {
    private Long id;
    private Long memberid;
    private String title;
    private String keyword;
    private String content;
    private Date createdate;
    private Date updatedate;
    private String nickname;
}
