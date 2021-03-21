package com.cplanet.toring.service;

import com.cplanet.toring.dto.TestDataRequestDto;
import com.cplanet.toring.dto.TestDataResponseDto;
import com.cplanet.toring.mapper.TestDataMapper;
import com.cplanet.toring.repository.TestDataRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestDataService {


    private final TestDataRepository testDataRepository;
    private final TestDataMapper testDataMapper;


    public TestDataService(TestDataRepository testDataRepository
                           ,TestDataMapper testDataMapper
                           ) {
        this.testDataRepository = testDataRepository;
        this.testDataMapper = testDataMapper;
    }

    @Transactional
    public Long save(TestDataRequestDto dto) {
        return testDataRepository.save(dto.toEntity()).getId();
    }
//    public Long save(PostsSaveRequestDto dto) {
//        return postsRepository.save(dto.toEntity()).getId();
//    }


    public List<TestDataResponseDto> findAll() {
        return testDataRepository.findAll().stream().map(TestDataResponseDto::new).collect(Collectors.toList());
    }


    public List<String> searchStr(String str) {

        return testDataMapper.selectByStrSearch(str);

    }


}
