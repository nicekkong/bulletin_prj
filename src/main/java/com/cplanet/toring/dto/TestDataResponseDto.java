package com.cplanet.toring.dto;


import com.cplanet.toring.domain.TestData;
import lombok.Getter;

@Getter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDataResponseDto {

    private Long id;

//    @JsonProperty(value="int_data")
    private Integer intData;

//    @JsonProperty(value="string_data")
    private String stringData;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(value="create_date")
    private String createDate;

//    @JsonIgnore
    private String updateDate;

    public TestDataResponseDto(TestData entity) {
        this.id = entity.getId();
        this.intData = entity.getIntData();
        this.stringData = entity.getStringData();
        this.createDate = entity.getCreated();
        this.updateDate = entity.getUpdated();
    }
}
