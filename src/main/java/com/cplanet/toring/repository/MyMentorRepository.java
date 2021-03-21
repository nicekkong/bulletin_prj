package com.cplanet.toring.repository;

import com.cplanet.toring.domain.MyMentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyMentorRepository extends JpaRepository<MyMentor, Long> {

    List<MyMentor> findTop2ByMemberIdOrderByCreateDate(Long member);

//    @Query(value = "select m from MyMentor m left join  m.contents  c inner join fetch m.profile p where m.memberId= :memberId and c.status = 'display' " +
//            "order by m.createDate desc, c.createDate desc",
//            countQuery="select count(m) from MyMentor m where m.memberId= :memberId")
//    Page<MyMentor> findByMemberIdOrderByCreateDateDesc(Long memberId, Pageable page);
//
//    @Query(value = "select m from MyMentor m left join fetch  m.contents  c inner join fetch m.profile p where m.memberId= :memberId and c.status = 'display' " +
//            "order by m.createDate desc, c.createDate desc",
//            countQuery="select count(m) from MyMentor m where m.memberId= :memberId")
//    List<MyMentor> findByMemberIdAndStatusOrderByCreateDateDesc(Long memberId);


    @Query(value = "select m from MyMentor m inner join fetch m.profile inner join m.mentor " +
            "where m.mentor.memberStatus = 'OK' and m.memberId = :memberId " +
            "order by m.createDate desc")
    List<MyMentor> findByMentorListByMemberId(Long memberId);

    @Query(value = "select m from MyMentor m inner join fetch m.profile inner join m.mentor " +
            "where m.mentor.memberStatus = 'OK' and m.memberId = :memberId " +
            "order by m.createDate desc",
            countQuery = "select count(m) from MyMentor m inner join m.profile inner join m.mentor " +
                    "where m.mentor.memberStatus = 'OK' and m.memberId = :memberId"
    )
    Page<MyMentor> findByMentorByMemberId(Long memberId, Pageable page);

}
