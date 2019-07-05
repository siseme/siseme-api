package me.sise.api.domain.dto;

import me.sise.api.infrastructure.persistence.jpa.entity.RegionStats;

import java.util.Comparator;
import java.util.List;

public class RangeRegionStats {

    private RegionStats startDateRegionStats;
    private RegionStats endDateRegionStats;

    RangeRegionStats(List<RegionStats> rangeRegionStats) {
        if (rangeRegionStats.size() != 2) {
            throw new IllegalArgumentException();
        }
        rangeRegionStats.sort(Comparator.comparing(RegionStats::getDate));
        startDateRegionStats = rangeRegionStats.get(0);
        endDateRegionStats = rangeRegionStats.get(1);
    }

    public double getIncreaseRateOfTrade() {
        return format((endDateRegionStats.getAveragePriceOfTrade() - startDateRegionStats.getAveragePriceOfTrade())
            / startDateRegionStats.getAveragePriceOfTrade());
    }

    public double getIncreaseRateOfJeonsei() {
        return format((endDateRegionStats.getAveragePriceOfJeonse() - startDateRegionStats.getAveragePriceOfJeonse())
            / startDateRegionStats.getAveragePriceOfJeonse());
    }

    private double format(double x) {
        return Math.round((x * 100)) / 100.0;
    }

}
