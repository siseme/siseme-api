package me.sise.api.domain.service;

import me.sise.api.application.common.util.DoubleUtils;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeRanks;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeRanksType;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TradeRanksRepository;
import me.sise.api.interfaces.v2.dto.TradeRankResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.sise.api.infrastructure.persistence.jpa.entity.TradeRanks.UNDETERMINED;
import static me.sise.api.interfaces.v2.dto.TradeRankResponse.NumberOfNewHighPriceRank;
import static me.sise.api.interfaces.v2.dto.TradeRankResponse.NumberOfTradeRank;
import static me.sise.api.interfaces.v2.dto.TradeRankResponse.UnitPriceRank;

@Service
public class TradeRankServiceImpl implements TradeRankService {

    private final TradeRanksRepository tradeRanksRepository;
    private final RegionRepository regionRepository;

    public TradeRankServiceImpl(TradeRanksRepository tradeRanksRepository,
                                RegionRepository regionRepository) {
        this.tradeRanksRepository = tradeRanksRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public TradeRankResponse getTradeRank(String startDate,
                                          String endDate,
                                          String regionType,
                                          String regionCode) {
        Region region = getRegion(regionType, regionCode);
        if (region == null) {
            throw new IllegalArgumentException("Cannot find region code : " + regionCode + ", regionType : " + regionType);
        }
        List<TradeRanks> tradeRanks = getTradeRanks(startDate, endDate, region);
        return TradeRankResponse.builder()
                                .withRegionName(region.getName())
                                .withRegionType(region.getType().name())
                                .withFromDate(startDate)
                                .withToDate(endDate)
                                .withNumberOfTradeRanks(extractNumberOfTradesRanks(tradeRanks))
                                .withNumberOfNewHighPriceRanks(extractNumberOfNewHighPriceRanks(tradeRanks))
                                .withUnitPriceRanks(extractUnitPriceRanks(tradeRanks))
                                .build();
    }

    private List<TradeRanks> getTradeRanks(String startDate, String endDate, Region region) {
        if (startDate.equals(endDate)) {
            return tradeRanksRepository.findByRegionAndDate(region, startDate);
        }
        List<TradeRanks> tradeRanks = tradeRanksRepository.findByRegionAndDateBetween(region, startDate, endDate);
        return mergeTradeRanksByRegionCode(tradeRanks);
    }

    private List<TradeRanks> mergeTradeRanksByRegionCode(List<TradeRanks> tradeRanks) {
        Map<String, TradeRanks> groupByRegionCode = tradeRanks.stream()
                                                              .collect(Collectors.toMap(TradeRanks::getRegionCode,
                                                                                        t -> {
                                                                                            TradeRanks tradeRank = new TradeRanks();
                                                                                            tradeRank.setUnitPrice(t.getUnitPrice());
                                                                                            tradeRank.setUnitPriceRanking(UNDETERMINED);
                                                                                            tradeRank.setNewHighPriceCount(t.getNewHighPriceCount());
                                                                                            tradeRank.setNewHighPriceCountRanking(
                                                                                                UNDETERMINED);
                                                                                            tradeRank.setTradeCount(t.getTradeCount());
                                                                                            tradeRank.setTradeCountRanking(UNDETERMINED);
                                                                                            tradeRank.setRegion(t.getRegion());
                                                                                            tradeRank.setRegionName(t.getRegionName());
                                                                                            tradeRank.setRegionType(t.getRegionType());
                                                                                            tradeRank.setRegionCode(t.getRegionCode());
                                                                                            tradeRank.setDate(t.getDate());
                                                                                            return tradeRank;
                                                                                        },
                                                                                        this::merge));
        return new ArrayList<>(groupByRegionCode.values());
    }

    private List<NumberOfTradeRank> extractNumberOfTradesRanks(List<TradeRanks> tradeRanks) {
        List<TradeRanks> numberOfTradesRanks = getTopTenRanks(tradeRanks, TradeRanksType.NUMBER_OF_TRADES);
        return numberOfTradesRanks.stream()
                                  .map(t -> {
                                      NumberOfTradeRank numberOfTradeRank = new NumberOfTradeRank();
                                      numberOfTradeRank.setRanking(t.getTradeCountRanking());
                                      numberOfTradeRank.setRegionCode(t.getRegionCode());
                                      numberOfTradeRank.setRegionType(t.getRegionType().name());
                                      numberOfTradeRank.setRegionName(t.getRegionName());
                                      numberOfTradeRank.setCount(t.getTradeCount());
                                      return numberOfTradeRank;
                                  }).collect(Collectors.toList());
    }

    private List<NumberOfNewHighPriceRank> extractNumberOfNewHighPriceRanks(List<TradeRanks> tradeRanks) {
        List<TradeRanks> numberOfNewHighPriceRanks = getTopTenRanks(tradeRanks, TradeRanksType.NUMBER_OF_NEW_HIGH_PRICE);
        return numberOfNewHighPriceRanks.stream()
                                        .map(t -> {
                                            NumberOfNewHighPriceRank numberOfNewHighPriceRank =
                                                new NumberOfNewHighPriceRank();
                                            numberOfNewHighPriceRank.setRanking(t.getNewHighPriceCountRanking());
                                            numberOfNewHighPriceRank.setRegionCode(t.getRegionCode());
                                            numberOfNewHighPriceRank.setRegionType(t.getRegionType().name());
                                            numberOfNewHighPriceRank.setRegionName(t.getRegionName());
                                            numberOfNewHighPriceRank.setTotalTradeCount(t.getTradeCount());
                                            numberOfNewHighPriceRank.setNewHighPriceCount(t.getNewHighPriceCount());
                                            return numberOfNewHighPriceRank;
                                        }).collect(Collectors.toList());
    }

    private List<UnitPriceRank> extractUnitPriceRanks(List<TradeRanks> tradeRanks) {
        List<TradeRanks> unitPriceRanks = getTopTenRanks(tradeRanks, TradeRanksType.UNIT_PRICE);
        return unitPriceRanks.stream()
                             .map(t -> {
                                 UnitPriceRank unitPriceRank = new UnitPriceRank();
                                 unitPriceRank.setRanking(t.getUnitPriceRanking());
                                 unitPriceRank.setRegionCode(t.getRegionCode());
                                 unitPriceRank.setRegionType(t.getRegionType().name());
                                 unitPriceRank.setRegionName(t.getRegionName());
                                 unitPriceRank.setUnitPrice(t.getUnitPrice());
                                 return unitPriceRank;
                             }).collect(Collectors.toList());
    }

    private TradeRanks merge(TradeRanks t1, TradeRanks t2) {
        TradeRanks tradeRank = new TradeRanks();
        tradeRank.setUnitPrice(mergeUnitPrice(t1, t2));
        tradeRank.setUnitPriceRanking(UNDETERMINED);
        tradeRank.setNewHighPriceCount(t1.getNewHighPriceCount() + t2.getNewHighPriceCount());
        tradeRank.setNewHighPriceCountRanking(
            UNDETERMINED);
        tradeRank.setTradeCount(t1.getTradeCount() + t2.getTradeCount());
        tradeRank.setTradeCountRanking(UNDETERMINED);
        tradeRank.setRegion(t1.getRegion());
        tradeRank.setRegionName(t1.getRegionName());
        tradeRank.setRegionType(t1.getRegionType());
        tradeRank.setRegionCode(t1.getRegionCode());
        tradeRank.setDate(StringUtils.compare(t1.getDate(), t2.getDate()) > 0 ? t1.getDate() : t2.getDate());
        return tradeRank;
    }

    private double mergeUnitPrice(TradeRanks t1, TradeRanks t2) {
        long count = t1.getTradeCount() + t2.getTradeCount();
        double sumOfUnitPrices = t1.getTradeCount() * t1.getUnitPrice() + t2.getTradeCount() * t2.getUnitPrice();
        return DoubleUtils.round(sumOfUnitPrices / count, 2);
    }

    private List<TradeRanks> getTopTenRanks(List<TradeRanks> tradeRanks, TradeRanksType rankType) {
        List<TradeRanks> sortedTradeRanks = new ArrayList<>(tradeRanks);
        if (rankType == TradeRanksType.NUMBER_OF_TRADES) {
            sortedTradeRanks.sort((t1, t2) -> (int) (t2.getTradeCount() - t1.getTradeCount()));
        } else if (rankType == TradeRanksType.NUMBER_OF_NEW_HIGH_PRICE) {
            sortedTradeRanks.sort((t1, t2) -> (int) (t2.getNewHighPriceCount() - t1.getNewHighPriceCount()));
        } else if (rankType == TradeRanksType.UNIT_PRICE) {
            sortedTradeRanks.sort((t1, t2) -> (int) (t2.getUnitPrice() - t1.getUnitPrice()));
        }
        IntStream.range(0, sortedTradeRanks.size())
                 .forEach(i -> {
                     if (rankType == TradeRanksType.NUMBER_OF_TRADES) {
                         sortedTradeRanks.get(i).setTradeCountRanking(i + 1);
                     } else if (rankType == TradeRanksType.NUMBER_OF_NEW_HIGH_PRICE) {
                         sortedTradeRanks.get(i).setNewHighPriceCountRanking(i + 1);
                     } else if (rankType == TradeRanksType.UNIT_PRICE) {
                         sortedTradeRanks.get(i).setUnitPriceRanking(i + 1);
                     }
                 });
        return sortedTradeRanks.subList(0, Math.min(10, sortedTradeRanks.size()));
    }

    private Region getRegion(String type, String code) {
        return regionRepository.findByCodeAndType(code, RegionType.fromString(type));
    }
}
