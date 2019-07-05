package me.sise.api.domain.service;

import lombok.RequiredArgsConstructor;
import me.sise.api.domain.dto.BubbleIndexModel;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionStats;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionStatsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionStatsServiceImpl implements RegionStatsService {

    private final RegionStatsRepository regionStatsRepository;
    private final RegionRepository regionRepository;

    @Override
    public BubbleIndexModel getBubbleIndex(String regionCode, String startDate, String endDate) {
        Region region = regionRepository.findByCode(regionCode);
        if (region == null) {
            throw new IllegalArgumentException("Cannot find region with regionCode : " + regionCode);
        }
        List<RegionStats> regionStats = regionStatsRepository.findByRegionAndDateIn(region, Arrays.asList(startDate, endDate));
        List<RegionStats> parentRegionStats = getParentRegionStats(region, startDate, endDate);
        return new BubbleIndexModel(region, regionStats, parentRegionStats);
    }

    private List<RegionStats> getParentRegionStats(Region region, String startDate, String endDate) {
        if (region.getParentRegionCode() == null) {
            return regionStatsRepository.findNationalAverageByDates(Arrays.asList(startDate, endDate));
        }
        Region parentRegion = regionRepository.findByCode(region.getParentRegionCode());
        return regionStatsRepository.findByRegionAndDateIn(parentRegion, Arrays.asList(startDate, endDate));
    }
}
