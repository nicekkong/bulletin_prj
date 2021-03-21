package com.cplanet.toring.controller;

import com.cplanet.toring.annotation.CurrentUser;
import com.cplanet.toring.domain.Member;
import com.cplanet.toring.domain.TestData;
import com.cplanet.toring.domain.security.MemberPrincipal;
import com.cplanet.toring.dto.response.MaskResponseDto;
import com.cplanet.toring.dto.TestDataResponseDto;
import com.cplanet.toring.repository.TestDataRepository;
import com.cplanet.toring.service.TestDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class SampleRestController {

    private final static Logger logger = LoggerFactory.getLogger(SampleRestController.class);


    final private TestDataService testDataService;
    final private TestDataRepository testDataRepository;
    final private RestTemplate restTemplate;

    public SampleRestController(TestDataService testDataService
                                ,TestDataRepository testDataRepository
                                ,RestTemplate restTemplate
    ) {
        this.testDataService = testDataService;
        this.testDataRepository = testDataRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/time")
    public String timme() {
        return "현재 시간 : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @GetMapping(value = "/data")
    public List<TestDataResponseDto> data() {
        List<TestDataResponseDto> result = testDataService.findAll();
        return result;
    }


    @GetMapping(value = "/data2")
    public List<TestDataResponseDto> data2() {
        List<TestData> result = testDataRepository.findAll();

        return result.stream().map(TestDataResponseDto::new).collect(Collectors.toList());

    }


    @GetMapping(value="mask")
    public MaskResponseDto mask(@CurrentUser Member user, Principal principal, @AuthenticationPrincipal MemberPrincipal member) {
        HashMap<String, String> param = new  HashMap<>();
        param.put("address", "서울특별시 서초구 잠원동");

//        MaskResponseDto response = restTemplate.getForObject("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}", MaskResponseDto.class,  param);

        ResponseEntity<MaskResponseDto> responseEntity = restTemplate.getForEntity("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}", MaskResponseDto.class,  param);

        logger.debug("result => {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

}
