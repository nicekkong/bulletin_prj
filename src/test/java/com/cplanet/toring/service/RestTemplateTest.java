package com.cplanet.toring.service;

import com.cplanet.toring.dto.response.MaskResponseDto;
import com.cplanet.toring.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value="local")
public class RestTemplateTest {


    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void restTemplateTest() {

        RestTemplate restTemplate = new RestTemplate();

        HashMap<String, Object> result = new  HashMap<>();

        HashMap<String, String> param = new  HashMap<>();
        param.put("address", "서울특별시 서초구 잠원동");

        result = restTemplate.getForObject("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json", result.getClass(),  param);


        System.out.println(result.toString());
    }

    @Test
    public void restTemplateTest2() {

        HashMap<String, String> param = new  HashMap<>();
        param.put("address", "서울특별시 서초구 잠원동");

        MaskResponseDto response = restTemplate.getForObject("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}", MaskResponseDto.class,  param);
        System.out.println(response.toString());

        ResponseEntity<MaskResponseDto> responseEntity = restTemplate.getForEntity("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}", MaskResponseDto.class,  param);
        System.out.println("STATUS CODE => " + responseEntity.getStatusCodeValue() + responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Value("${my.email}")
    String email;

    @Value("${number}")
    int number;

    @Value("${env.servers}")
    List<String> servers;

    @Test
    public void propertiesTest() {

        System.out.println(email);
        System.out.println(number*2);
        servers.forEach(System.out::println);
    }

    @Autowired
    SampleCacheService sampleCacheService;

    @Test
    public void cacheTest() {

        System.out.println(sampleCacheService.getCacheEmail("nicekkong"));
        System.out.println("========================================");
        System.out.println(sampleCacheService.getCacheEmail("nicekkong"));
        System.out.println("========================================");
        sampleCacheService.cacheEvict("threeMinCache", "nicekkong");
        System.out.println("========================================");
        System.out.println(sampleCacheService.getCacheEmail("nicekkong"));
    }


    @Test
    public void testHumanize() {

        LocalDateTime time = LocalDateTime.of(2020, 4,5, 11, 10, 30);
        System.out.println(Duration.between(LocalTime.of(time.getHour(), time.getMinute()), LocalTime.of(time.getHour(), time.getMinute())).toHours());

        System.out.println(DateUtils.toHumanizeDateTime(time));

    }

}
