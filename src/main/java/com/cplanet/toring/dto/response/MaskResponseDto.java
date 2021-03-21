package com.cplanet.toring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class MaskResponseDto {

    private String address;
    private int count;
    private List<Store> stores;

    @Getter @Setter @ToString
    static class Store {

        private String addr;
        private String code;
        private LocalDateTime createdAt;
        private double lat;
        private double lng;
        private String name;
        @JsonProperty(value ="remain_stat")
        private String remainStat;
        @JsonProperty(value ="stock_at")
        private String stockAt;
        private String type;

        private void setStockAt(String result) {
            this.stockAt = "11111111";
        }

    }
}

