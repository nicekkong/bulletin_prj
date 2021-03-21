package com.cplanet.toring.domain;


import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="test_data")
@EntityListeners(AuditingEntityListener.class)
public class TestData extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer intData;

    private String stringData;

}
