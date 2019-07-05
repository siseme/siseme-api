package me.sise.api.domain.dto;

import lombok.Data;

@Data
public class RegionCodeModel {
    private String type;
    private String admCode;
    private String admCodeNm;
    private String lowestAdmCodeNm;
}
