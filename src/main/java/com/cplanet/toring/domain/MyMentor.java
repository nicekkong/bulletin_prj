package com.cplanet.toring.domain;


import com.cplanet.toring.domain.common.AuditEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tr_my_mentor")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MyMentor extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;


    @Column(name = "mentor_id")
    private Long mentorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Member mentor;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Profile profile;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mentor_id",  referencedColumnName = "member_id", insertable = false, updatable = false)
//    private Profile profile;



//    @Column(name = "mentor_id")
//    private Long mentorId;

    // 실제로는 @ManyToOne 관계이지만, @JoinTable에서 주키가 아닌 컬럼 간의 조인을 지원하지 않으므로
    // @ManyToMany 관계로 설정한다.
    // https://stackoverflow.com/questions/13885916/one-to-many-association-join-tables-with-non-primary-key-column-in-jpa
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "tr_member",
//        joinColumns = @JoinColumn(name = "id", referencedColumnName = "mentor_id", insertable = false, updatable = false, nullable = true),
//        inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "member_id", insertable = false, updatable = false, nullable = true))
//    private List<Profile> profile;





//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "tr_profile",
//        joinColumns = @JoinColumn(name="member_id", referencedColumnName = "mentor_id", insertable = false, updatable = false, nullable = true),
//        inverseJoinColumns = @JoinColumn(name ="member_id", referencedColumnName = "member_id", insertable = false, updatable = false, nullable = true))
//    private List<Contents> contents = new ArrayList<>();

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mentor_id")
//    private Member member;

}
