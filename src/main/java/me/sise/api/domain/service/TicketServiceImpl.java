package me.sise.api.domain.service;

import com.google.common.collect.Lists;
import me.sise.api.infrastructure.persistence.jpa.entity.AreaType;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.entity.Ticket;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeStats;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeType;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TicketRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TradeStatsRepository;
import me.sise.api.interfaces.v1.dto.response.V1MaxTradeResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1StatsResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeCountResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TradeStatsRepository tradeStatsRepository;
    private final RegionRepository regionRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TradeStatsRepository tradeStatsRepository,
                             RegionRepository regionRepository) {
        this.ticketRepository = ticketRepository;
        this.tradeStatsRepository = tradeStatsRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public V1StatsResponse getStats(String regionCode, String date, String rangeType) {
        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyyMM"));
        int range = Integer.valueOf(rangeType);
        String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        String pastDate = yearMonth.minusYears(range)
                                   .format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        List<TradeStats> tradeStatsList = tradeStatsRepository.findByDateBetweenAndRegionCodeAndTradeType(pastDate,
                                                                                                          baseDate,
                                                                                                          regionCode,
                                                                                                          TradeType.TICKET);
        Map<AreaType, List<TradeStats>> collect = tradeStatsList.stream()
                                                                .collect(Collectors.groupingBy(TradeStats::getAreaType));
        V1StatsResponse v1StatsResponse = new V1StatsResponse();
        for (AreaType areaType : collect.keySet()) {
            List<TradeStats> statsList = collect.get(areaType);
            YearMonth tmpYearMonth = YearMonth.of(yearMonth.getYear(), yearMonth.getMonthValue());
            for (int i = 0; i <= range * 12; i++) {
                String yyyyMM = tmpYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                            .replace("-", "");
                if (statsList.stream()
                             .noneMatch(x -> x.getDate()
                                              .equals(yyyyMM))) {
                    TradeStats tmpStats = new TradeStats();
                    tmpStats.setId(-1L);
                    tmpStats.setRegionCode(regionCode);
                    tmpStats.setDate(yyyyMM);
                    tmpStats.setAreaType(areaType);
                    tmpStats.setSumMainPrice(0);
                    tmpStats.setCount(0);
                    statsList.add(tmpStats);
                }
                tmpYearMonth = tmpYearMonth.minusMonths(1);
            }
            statsList = statsList.stream()
                                 .sorted(Comparator.comparing(TradeStats::getDate))
                                 .collect(Collectors.toList());
            if (areaType == AreaType.TYPE_1) {
                v1StatsResponse.setType1TradeStatsList(statsList);
            } else if (areaType == AreaType.TYPE_2) {
                v1StatsResponse.setType2TradeStatsList(statsList);
            } else if (areaType == AreaType.TYPE_3) {
                v1StatsResponse.setType3TradeStatsList(statsList);
            } else if (areaType == AreaType.TYPE_4) {
                v1StatsResponse.setType4TradeStatsList(statsList);
            } else if (areaType == AreaType.TYPE_5) {
                v1StatsResponse.setType5TradeStatsList(statsList);
            }
        }
        return v1StatsResponse;
    }

    @Override
    public List<V1TradeCountResponse> getTradeCount(String regionCode, String date, String rangeType) {
        int length = regionCode.length();
        int range = Integer.valueOf(rangeType);
        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyyMM"));
        List<Region> regionList = Lists.newArrayList();
        if (length == 2) {
            regionList = regionRepository.findByType(RegionType.SIDO);
        } else if (length == 5) {
            regionList = regionRepository.findByCodeLikeAndType(regionCode.substring(0, 2) + "%", RegionType.GUNGU);
        } else if (length == 8) {
            regionList = regionRepository.findByCodeLikeAndType(regionCode.substring(0, 5) + "%", RegionType.DONG);
        }
        List<TradeStats> tempList = Lists.newArrayList();
        for (Region region : regionList) {
            YearMonth baseDate = yearMonth;//.format(DateTimeFormatter.ofPattern("yyyyMM")).replace("-", "");
            YearMonth pastDate = yearMonth.minusYears(range);
            while (baseDate.compareTo(pastDate) >= 0) {
                String formatDate = baseDate.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                            .replace("-", "");
                tradeStatsRepository.findByDateAndRegionCodeAndTradeType(formatDate, region.getCode(), TradeType.TICKET)
                                    .stream()
                                    .reduce((a, b) -> {
                                        TradeStats tradeStats = new TradeStats();
                                        tradeStats.setCount(a.getCount() + b.getCount());
                                        tradeStats.setSumMainPrice(a.getSumMainPrice() + b.getSumMainPrice());
                                        tradeStats.setDate(a.getDate());
                                        tradeStats.setAreaType(AreaType.TYPE_6);
                                        tradeStats.setRegionCode(a.getRegionCode());
                                        tradeStats.setTradeType(TradeType.TICKET);
                                        return tradeStats;
                                    })
                                    .ifPresent(tempList::add);
                baseDate = baseDate.minusMonths(1);
            }
        }
        Map<String, List<TradeStats>> collect = tempList.stream()
                                                        .collect(Collectors.groupingBy(TradeStats::getRegionCode));
        List<V1TradeCountResponse> result = Lists.newArrayList();
        for (String code : collect.keySet()) {
            V1TradeCountResponse v1TradeCountResponse = new V1TradeCountResponse();
            v1TradeCountResponse.setCode(code);
            v1TradeCountResponse.setName(regionRepository.findByCode(code)
                                                         .getName());
            List<TradeStats> statsList = collect.get(code);
            YearMonth tmpYearMonth = YearMonth.of(yearMonth.getYear(), yearMonth.getMonthValue());
            for (int i = 0; i <= range * 12; i++) {
                String yyyyMM = tmpYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                            .replace("-", "");
                if (statsList.stream()
                             .noneMatch(x -> x.getDate()
                                              .equals(yyyyMM))) {
                    TradeStats tradeStats = new TradeStats();
                    tradeStats.setCount(0);
                    tradeStats.setSumMainPrice(0);
                    tradeStats.setDate(yyyyMM);
                    tradeStats.setAreaType(AreaType.TYPE_6);
                    tradeStats.setRegionCode(statsList.stream()
                                                      .findFirst()
                                                      .get()
                                                      .getRegionCode());
                    tradeStats.setTradeType(TradeType.TICKET);
                    statsList.add(tradeStats);
                }
                tmpYearMonth = tmpYearMonth.minusMonths(1);
            }
            v1TradeCountResponse.setTradeStatsList(statsList);
            result.add(v1TradeCountResponse);
        }
        return result;
    }

    @Override
    public V1PageResponse<V1MaxTradeResponse> getMaxTradeListByPaging(String date,
                                                                      String regionCode,
                                                                      String searchType,
                                                                      String pageNo,
                                                                      String size,
                                                                      String sortType,
                                                                      String areaType,
                                                                      String rangeType) {
        Page<Ticket> tradeList;
        String[] sortTypes = sortType.split("-");
        Sort.Direction direction;
        if (sortTypes.length == 2) {
            direction = sortTypes[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        Double startArea = 0d;
        Double endArea = 0d;
        if (areaType.equals("1")) {
            startArea = 0d;
            endArea = 49d;
        } else if (areaType.equals("2")) {
            startArea = 49.0000001d;
            endArea = 60d;
        } else if (areaType.equals("3")) {
            startArea = 60.0000001d;
            endArea = 85d;
        } else if (areaType.equals("4")) {
            startArea = 85.0000001d;
            endArea = 135d;
        } else if (areaType.equals("5")) {
            endArea = 135.0000001d;
        }
        PageRequest pageRequest;
        if (RegionType.SIDO.name()
                           .equals(searchType.toUpperCase())) {
            pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                          Integer.valueOf(size),
                                          new Sort(direction, sortTypes[0].equals("regionType") ? "dongName" : sortTypes[0]));
            tradeList = areaType.equals("0") ?
                ticketRepository.findByDateAndSidoCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                ticketRepository.findByDateAndSidoCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : ticketRepository.findByDateAndSidoCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        } else if (RegionType.GUNGU.name()
                                   .equals(searchType.toUpperCase())) {
            pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                          Integer.valueOf(size),
                                          new Sort(direction, sortTypes[0].equals("regionType") ? "dongName" : sortTypes[0]));
            tradeList = areaType.equals("0") ?
                ticketRepository.findByDateAndGunguCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                ticketRepository.findByDateAndGunguCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : ticketRepository.findByDateAndGunguCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        } else {
            pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                          Integer.valueOf(size),
                                          new Sort(direction, sortTypes[0].equals("regionType") ? "dongName" : sortTypes[0]));
            tradeList = areaType.equals("0") ?
                ticketRepository.findByDateAndDongCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                ticketRepository.findByDateAndDongCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : ticketRepository.findByDateAndDongCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        }
        V1PageResponse<V1MaxTradeResponse> v1PageResponse = new V1PageResponse<>();
        v1PageResponse.setPageNumber(tradeList.getNumber());
        v1PageResponse.setTotalElements(tradeList.getTotalElements());
        v1PageResponse.setTotalPages(tradeList.getTotalPages());
        v1PageResponse.setContents(tradeList.getContent()
                                            .stream()
                                            .filter(x -> x.getTradeType() == TradeType.TICKET)
                                            .map(x -> this.transform(x, Integer.valueOf(rangeType)))
                                            .collect(Collectors.toList()));
        return v1PageResponse;
    }

    private V1MaxTradeResponse transform(Ticket ticket, Integer range) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setDate(ticket.getDate()
                                      .substring(0, 4) + "." + ticket.getDate()
                                                                     .substring(4, 6) + "." + ticket.getMonthType()
                                                                                                    .getWord());
        v1TradeResponse.setDateName(/*openApiTradeInfoRepository.findOne(trade.getOpenApiTradeInfoId()).getDay()*/
                                    ticket.getMonthType()
                                          .getRange());
        v1TradeResponse.setRegionName(ticket.getDongName());
        v1TradeResponse.setName(ticket.getName());
        v1TradeResponse.setFloor(String.valueOf(ticket.getFloor()));
        v1TradeResponse.setArea(String.valueOf(ticket.getArea()));
        v1TradeResponse.setPrice(ticket.getMainPrice());
        YearMonth yearMonth = YearMonth.parse(ticket.getDate(), DateTimeFormatter.ofPattern("yyyyMM"));
        String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        String pastDate = yearMonth.minusYears(range)
                                   .format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        List<Ticket> pastTradeList = ticketRepository.findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(pastDate,
                                                                                                                 baseDate,
                                                                                                                 ticket.getDongCode(),
                                                                                                                 ticket.getName(),
                                                                                                                 ticket.getArea());
        Integer max = pastTradeList.stream()
                                   .filter(x -> !Objects.equals(x.getOpenApiTicketInfoId(), ticket.getOpenApiTicketInfoId()))
                                   .map(Ticket::getMainPrice)
                                   .max(Integer::compareTo)
                                   .orElse(0);
        v1TradeResponse.setPastMaxPrice(max);

/*
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isNewData = trade.getCreatedDateTime().plusDays(2).isAfter(currentTime);
*/
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime baseTime = LocalDateTime.of(currentTime.getYear(),
                                                  currentTime.getMonthValue(),
                                                  currentTime.getDayOfMonth(),
                                                  5,
                                                  59,
                                                  59);
        LocalDateTime createdDateTime = ticket.getCreatedDateTime();
        boolean isNewData = createdDateTime.isAfter(baseTime);
        v1TradeResponse.setIsNewData(isNewData);
        V1MaxTradeResponse v1MaxTradeResponse = new V1MaxTradeResponse();
        v1MaxTradeResponse.setTrade(v1TradeResponse);
        v1MaxTradeResponse.setPastTradeList(pastTradeList.stream()
                                                         .map(this::getSimpleData)
                                                         .sorted(Comparator.comparing(V1TradeResponse::getDate))
                                                         .collect(Collectors.toList()));
        return v1MaxTradeResponse;
    }

    private V1TradeResponse getSimpleData(Ticket ticket) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setDate(ticket.getDate());
        v1TradeResponse.setDateName(ticket.getMonthType()
                                          .getRange());
        v1TradeResponse.setName(ticket.getName());
/*
        v1TradeResponse.setFloor(trade.getFloor());
        v1TradeResponse.setArea(trade.getArea());
*/
        v1TradeResponse.setPrice(ticket.getMainPrice());
        return v1TradeResponse;
    }
}
