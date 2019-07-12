package me.sise.api.domain.service;

import me.sise.api.interfaces.v1.dto.response.V1RegionResponse;

import java.util.List;

public interface RegionCodeService {
    V1RegionResponse getRegion(String code);

    List<V1RegionResponse> getResionListByCodeLike(String code);
    List<V1RegionResponse> getResionListByType(String code);

    void readRegionCodeJsonFile();

    List<V1RegionResponse> getRegionListByTypeAndCodeLike(String type, String code);

    List<V1RegionResponse> getRegions(String keyword);

    List<V1RegionResponse> getApts(String code, String tradeType);

    V1RegionResponse getApt(String dongCode, String name, String tradeType);

    V1RegionResponse getRegionByFullName(String regionName);
}
