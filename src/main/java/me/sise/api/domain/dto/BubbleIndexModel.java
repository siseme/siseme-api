package me.sise.api.domain.dto;

import lombok.Data;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionStats;

import java.util.List;

@Data
public class BubbleIndexModel {

    private Region region;
    private RangeRegionStats regionRangeRegionStats;
    private RangeRegionStats parentRegionRangeRegionStats;

    public BubbleIndexModel(Region region,
                            List<RegionStats> regionStats,
                            List<RegionStats> parentRegionStats) {
        this.region = region;
        this.regionRangeRegionStats = new RangeRegionStats(regionStats);
        this.parentRegionRangeRegionStats = new RangeRegionStats(parentRegionStats);
    }

    public double getBubbleIndex() {
        return (regionRangeRegionStats.getIncreaseRateOfTrade() - parentRegionRangeRegionStats.getIncreaseRateOfTrade())
            - (regionRangeRegionStats.getIncreaseRateOfJeonsei() - parentRegionRangeRegionStats.getIncreaseRateOfJeonsei());
    }
}
