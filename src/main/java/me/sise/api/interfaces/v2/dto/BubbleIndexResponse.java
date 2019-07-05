package me.sise.api.interfaces.v2.dto;

import lombok.Data;
import me.sise.api.domain.dto.BubbleIndexModel;

@Data
public class BubbleIndexResponse {

    private String regionName;
    private String regionCode;
    private double bubbleIndex;
    private double regionIncreaseRateOfTrade;
    private double regionIncreaseRateOfJeonsei;
    private double parentRegionIncreaseRateOfTrade;
    private double parentRegionIncreaseRateOfJeonsei;

    public static BubbleIndexResponse of(BubbleIndexModel bubbleIndexModel) {
        BubbleIndexResponse response = new BubbleIndexResponse();
        response.setRegionName(bubbleIndexModel.getRegion().getFullName());
        response.setRegionCode(bubbleIndexModel.getRegion().getCode());
        response.setBubbleIndex(bubbleIndexModel.getBubbleIndex());
        response.setRegionIncreaseRateOfTrade(bubbleIndexModel.getRegionRangeRegionStats().getIncreaseRateOfTrade());
        response.setRegionIncreaseRateOfJeonsei(bubbleIndexModel.getRegionRangeRegionStats().getIncreaseRateOfJeonsei());
        response.setParentRegionIncreaseRateOfTrade(bubbleIndexModel.getParentRegionRangeRegionStats().getIncreaseRateOfTrade());
        response.setParentRegionIncreaseRateOfJeonsei(bubbleIndexModel.getParentRegionRangeRegionStats().getIncreaseRateOfJeonsei());

        return response;
    }
}
