package com.cplanet.toring.repository;

import com.cplanet.toring.domain.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

    Page<Contents> findByMemberIdOrderByCreateDate(Long memberId, Pageable page);


    List<Contents> findTop3ByMemberIdOrderByCreateDateDesc(Long memberId);
}
