package me.sise.api.domain.service;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.repository.AggregateCount;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RentRankRepository;
import me.sise.api.interfaces.v2.dto.RentRankResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.sise.api.interfaces.v2.dto.RentRankResponse.RentCountRank;

@Service
public class RentRankServiceImpl implements RentRankService {

    private final RegionRepository regionRepository;
    private final RentRankRepository rentRankRepository;

    public RentRankServiceImpl(RegionRepository regionRepository,
                               RentRankRepository rentRankRepository) {
        this.regionRepository = regionRepository;
        this.rentRankRepository = rentRankRepository;
    }

    @Override
    public RentRankResponse getRentRank(String startDate, String endDate, String regionType, String regionCode) {
        Region region = getRegion(regionType, regionCode);
        if (region == null) {
            throw new IllegalArgumentException("Cannot find region. regionCode :  " + regionCode);
        }

        List<AggregateCount> aggregateRentCounts = rentRankRepository.findAggregateCountBetweenDate(region, startDate, endDate);
        return RentRankResponse.builder()
                               .withRegionName(region.getName())
                               .withRegionType(region.getType().name())
                               .withFromDate(startDate)
                               .withToDate(endDate)
                               .withRentCountRanks(convertToRentCountRank(aggregateRentCounts))
                               .build();
    }

    private List<RentCountRank> convertToRentCountRank(List<AggregateCount> rentCounts) {
        List<RentCountRank> ranks = getTopTenCountRank(rentCounts);
        IntStream.range(0, ranks.size())
                 .forEach(i -> ranks.get(i).setRanking(i + 1));
        return ranks;
    }

    private List<RentCountRank> getTopTenCountRank(List<AggregateCount> rentCounts) {
        List<RentCountRank> ranks = rentCounts.stream()
                                              .map(rentCount -> {
                                                  RentCountRank rentCountRank = new RentCountRank();
                                                  rentCountRank.setCount(rentCount.getCount());
                                                  rentCountRank.setRegionCode(rentCount.getRegionCode());
                                                  rentCountRank.setRegionName(rentCount.getRegionName());
                                                  rentCountRank.setRegionType(rentCount.getRegionType().name());
                                                  return rentCountRank;
                                              }).sorted((r1, r2) -> (int) (r2.getCount() - r1.getCount()))
                                              .collect(Collectors.toList());
        if (ranks.size() > 10) {
            return ranks.subList(0, 10);
        }
        return ranks;
    }

    private Region getRegion(String type, String code) {
        return regionRepository.findByCodeAndType(code, RegionType.fromString(type));
    }
}
