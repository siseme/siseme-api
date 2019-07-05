package me.sise.api.interfaces.v2.dto;

import lombok.Data;

@Data
public class ShortUrlCreateRequest {
    private String webUrl;
    private String regionCode;
    private String startDate;
    private String endDate;
    private String regionType;
    private String regionName;
    private String tradeType;
}
