package me.sise.api.domain.service;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.repository.AggregateCount;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TicketRankRepository;
import me.sise.api.interfaces.v2.dto.TicketRankResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.sise.api.interfaces.v2.dto.TicketRankResponse.TicketCountRank;

@Service
public class TicketRankServiceImpl implements TicketRankService {

    private final RegionRepository regionRepository;
    private final TicketRankRepository ticketRankRepository;

    public TicketRankServiceImpl(RegionRepository regionRepository,
                                 TicketRankRepository ticketRankRepository) {
        this.regionRepository = regionRepository;
        this.ticketRankRepository = ticketRankRepository;
    }

    @Override
    public TicketRankResponse getTicketRank(String startDate, String endDate, String regionType, String regionCode) {
        Region region = getRegion(regionType, regionCode);
        if (region == null) {
            throw new IllegalArgumentException("Cannot find region. regionCode :  " + regionCode);
        }

        List<AggregateCount> aggregateTicketCounts = ticketRankRepository.findAggregateCountBetweenDate(region, startDate, endDate);
        return TicketRankResponse.builder()
                                 .withRegionName(region.getName())
                                 .withRegionType(region.getType().name())
                                 .withFromDate(startDate)
                                 .withToDate(endDate)
                                 .withTicketCountRanks(convertToTicketCountRanks(aggregateTicketCounts))
                                 .build();
    }

    private List<TicketCountRank> convertToTicketCountRanks(List<AggregateCount> ticketCounts) {
        List<TicketCountRank> ranks = getTopTenCountRanks(ticketCounts);
        IntStream.range(0, ranks.size())
                 .forEach(i -> ranks.get(i).setRanking(i + 1));
        return ranks;
    }

    private List<TicketCountRank> getTopTenCountRanks(List<AggregateCount> ticketCounts) {
        List<TicketCountRank> ranks = ticketCounts.stream()
                                                  .map(ticketCount -> {
                                                      TicketCountRank ticketCountRank = new TicketCountRank();
                                                      ticketCountRank.setCount(ticketCount.getCount());
                                                      ticketCountRank.setRegionCode(ticketCount.getRegionCode());
                                                      ticketCountRank.setRegionName(ticketCount.getRegionName());
                                                      ticketCountRank.setRegionType(ticketCount.getRegionType().name());
                                                      return ticketCountRank;
                                                  })
                                                  .sorted((t1, t2) -> (int) (t2.getCount() - t1.getCount()))
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
