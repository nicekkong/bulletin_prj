package com.cplanet.toring.mapper;

import com.cplanet.toring.domain.TestData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TestDataMapper {

    @Select("SELECT * FROM test_data WHERE id=#{id}")
    TestData selectTestDataById(@Param("id")Long id);

    List<String> selectByStrSearch(@Param("str")String str);
}
