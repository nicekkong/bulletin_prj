package com.cplanet.toring.service;

import com.cplanet.toring.dto.response.MaskResponseDto;
import com.cplanet.toring.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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


    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void restTemplateTest() {
        HashMap<String, String> param = new HashMap<>();
        param.put("address", "서울특별시 서초구 잠원동");

        MaskResponseDto mockResponse = new MaskResponseDto();
        mockResponse.setAddress("서울특별시 서초구 잠원동");
        mockResponse.setCount(10);

        Mockito.when(restTemplate.getForObject(
                Mockito.eq("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}"),
                Mockito.eq(MaskResponseDto.class),
                Mockito.eq(param)
        )).thenReturn(mockResponse);

        MaskResponseDto response = restTemplate.getForObject(
                "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}",
                MaskResponseDto.class,
                param
        );

        assertEquals("서울특별시 서초구 잠원동", response.getAddress());
        assertEquals(10, response.getCount());
    }

    @Test
    public void restTemplateTest2() {
        HashMap<String, String> param = new HashMap<>();
        param.put("address", "서울특별시 서초구 잠원동");

        MaskResponseDto mockResponse = new MaskResponseDto();
        mockResponse.setAddress("서울특별시 서초구 잠원동");
        mockResponse.setCount(20);
        ResponseEntity<MaskResponseDto> mockEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(
                Mockito.eq("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}"),
                Mockito.eq(MaskResponseDto.class),
                Mockito.eq(param)
        )).thenReturn(mockEntity);

        ResponseEntity<MaskResponseDto> responseEntity = restTemplate.getForEntity(
                "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address={address}",
                MaskResponseDto.class,
                param
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(20, responseEntity.getBody().getCount());
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
