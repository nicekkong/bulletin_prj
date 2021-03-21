package com.cplanet.toring.repository;

import com.cplanet.toring.domain.TestData;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestDataRepository extends JpaRepository<TestData, Integer> {

    List<TestData> findByStringDataContains(String searchStr);

    @Query("SELECT t FROM TestData t WHERE t.stringData like %:search%")
    List<TestData> findBySearch(@Param("search")String search);

    @Transactional
    @Modifying
    @Query("DELETE FROM TestData t WHERE t.id = :id")
    void deleteByIdInQuery(@Param("id") Long id);
}
