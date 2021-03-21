package com.cplanet.toring.domain.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {

    @CreatedDate
    @JsonIgnore
    private LocalDateTime createDate;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updateDate;


    /**
     * createDate와 updateDate는 LocalDateTime 형식이라 JSON으로 표현하기엔 부적합 하다.
     * 따러서, Entity의 생성일, 수정일은 별도의 created, updated 필드를 DateUtils methode를 통해 변환하여 제공한다.
     */
    @Transient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String created;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String updated;

}
