package me.sise.api.domain.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.sise.api.infrastructure.persistence.jpa.entity.Apartment;
import me.sise.api.infrastructure.persistence.jpa.entity.AreaType;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.entity.Rent;
import me.sise.api.infrastructure.persistence.jpa.entity.Ticket;
import me.sise.api.infrastructure.persistence.jpa.entity.Trade;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeStats;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeType;
import me.sise.api.infrastructure.persistence.jpa.repository.ApartmentRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.OpenApiTradeInfoRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RentRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TicketRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TradeRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.TradeStatsRepository;
import me.sise.api.interfaces.v1.dto.response.V1DetailResponse;
import me.sise.api.interfaces.v1.dto.response.V1MaxTradeResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1StatsResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeCountResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeResponse;
import me.sise.api.interfaces.v2.dto.StatsResponse;
import me.sise.api.interfaces.v2.dto.SummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final TicketRepository ticketRepository;
    private final RentRepository rentRepository;
    private final TradeStatsRepository tradeStatsRepository;
    private final RegionRepository regionRepository;
    private final OpenApiTradeInfoRepository openApiTradeInfoRepository;
    private final ApartmentRepository apartmentRepository;

    public TradeServiceImpl(TradeRepository tradeRepository,
                            TicketRepository ticketRepository,
                            RentRepository rentRepository,
                            TradeStatsRepository tradeStatsRepository,
                            RegionRepository regionRepository,
                            OpenApiTradeInfoRepository openApiTradeInfoRepository,
                            ApartmentRepository apartmentRepository) {
        this.tradeRepository = tradeRepository;
        this.ticketRepository = ticketRepository;
        this.rentRepository = rentRepository;
        this.tradeStatsRepository = tradeStatsRepository;
        this.regionRepository = regionRepository;
        this.openApiTradeInfoRepository = openApiTradeInfoRepository;
        this.apartmentRepository = apartmentRepository;
    }

/*
    @Override
    public List<V1MaxTradeResponse> getMaxTradeList(String date, String regionCode, String searchType) {
        List<Trade> tradeList = Lists.newArrayList();
        if (RegionType.SIDO.name().equals(searchType.toUpperCase())) {
            tradeList = tradeRepository.findByDateAndSidoCode(date, regionCode);
        } else if (RegionType.GUNGU.name().equals(searchType.toUpperCase())) {
            tradeList = tradeRepository.findByDateAndGunguCode(date, regionCode);
        } else if (RegionType.DONG.name().equals(searchType.toUpperCase())) {
            tradeList = tradeRepository.findByDateAndDongCode(date, regionCode);
        } else {
            log.error("일치하는 지역 정보가 존재하지 않습니다. searchType : {}, regionCode : {}", searchType, regionCode);
        }
        return tradeList.stream()
                        .filter(x -> x.getTradeType() == TradeType.TRADE)
                        .sorted(Comparator.comparingInt(x -> x.getMonthType().getOrder()))
                        .map(this::transform)
                        .collect(Collectors.toList());
    }
*/

    @Override
    public V1PageResponse<V1MaxTradeResponse> getMaxTradeListByPaging(String date,
                                                                      String regionCode,
                                                                      String searchType,
                                                                      String pageNo,
                                                                      String size,
                                                                      String sortType,
                                                                      String areaType,
                                                                      String rangeType) {
        Page<Trade> tradeList;
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
                tradeRepository.findByDateAndSidoCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                tradeRepository.findByDateAndSidoCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : tradeRepository.findByDateAndSidoCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        } else if (RegionType.GUNGU.name()
                                   .equals(searchType.toUpperCase())) {
            pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                          Integer.valueOf(size),
                                          new Sort(direction, sortTypes[0].equals("regionType") ? "dongName" : sortTypes[0]));
            tradeList = areaType.equals("0") ?
                tradeRepository.findByDateAndGunguCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                tradeRepository.findByDateAndGunguCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : tradeRepository.findByDateAndGunguCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        } else {
            pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                          Integer.valueOf(size),
                                          new Sort(direction, sortTypes[0].equals("regionType") ? "dongName" : sortTypes[0]));
            tradeList = areaType.equals("0") ?
                tradeRepository.findByDateAndDongCode(date, regionCode, pageRequest)
                : (areaType.equals("5") ?
                tradeRepository.findByDateAndDongCodeAndAreaGreaterThanEqual(date, regionCode, endArea, pageRequest)
                : tradeRepository.findByDateAndDongCodeAndAreaBetween(date, regionCode, startArea, endArea, pageRequest));
        }
        V1PageResponse<V1MaxTradeResponse> v1PageResponse = new V1PageResponse<>();
        v1PageResponse.setPageNumber(tradeList.getNumber());
        v1PageResponse.setTotalElements(tradeList.getTotalElements());
        v1PageResponse.setTotalPages(tradeList.getTotalPages());
        v1PageResponse.setContents(tradeList.getContent()
                                            .stream()
                                            .filter(x -> x.getTradeType() == TradeType.TRADE)
                                            .map(x -> this.transform(x, Integer.valueOf(rangeType)))
                                            .collect(Collectors.toList()));
        return v1PageResponse;
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
                                                                                                          TradeType.TRADE);
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
    public StatsResponse getStatsMap(String tradeType, String regionCode, String date, String rangeType) {
        TradeType type = TradeType.fromString(tradeType);
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
                                                                                                          type);
        Map<AreaType, List<TradeStats>> collect = tradeStatsList.stream()
                                                                .collect(Collectors.groupingBy(TradeStats::getAreaType));
        StatsResponse v1StatsResponse = new StatsResponse();
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
            List<StatsResponse.Stats> result = statsList.stream()
                                                        .map(x -> {
                                                              StatsResponse.Stats stats = new StatsResponse.Stats();
                                                              stats.setDate(x.getDate());
                                                              stats.setRegionCode(x.getRegionCode());
                                                              stats.setSumMainPrice(x.getSumMainPrice());
                                                              stats.setCount(x.getCount());
                                                              stats.setAreaType(x.getAreaType()
                                                                                 .getFullName());
                                                              stats.setTradeType(x.getTradeType());
                                                              return stats;
                                                          })
                                                        .sorted(Comparator.comparing(StatsResponse.Stats::getDate))
                                                        .collect(Collectors.toList());
            if (areaType == AreaType.TYPE_1) {
                v1StatsResponse.setType1TradeStatsList(result);
            } else if (areaType == AreaType.TYPE_2) {
                v1StatsResponse.setType2TradeStatsList(result);
            } else if (areaType == AreaType.TYPE_3) {
                v1StatsResponse.setType3TradeStatsList(result);
            } else if (areaType == AreaType.TYPE_4) {
                v1StatsResponse.setType4TradeStatsList(result);
            } else if (areaType == AreaType.TYPE_5) {
                v1StatsResponse.setType5TradeStatsList(result);
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
                tradeStatsRepository.findByDateAndRegionCodeAndTradeType(formatDate, region.getCode(), TradeType.TRADE)
                                    .stream()
                                    .reduce((a, b) -> {
                                        TradeStats tradeStats = new TradeStats();
                                        tradeStats.setCount(a.getCount() + b.getCount());
                                        tradeStats.setSumMainPrice(a.getSumMainPrice() + b.getSumMainPrice());
                                        tradeStats.setDate(a.getDate());
                                        tradeStats.setAreaType(AreaType.TYPE_6);
                                        tradeStats.setRegionCode(a.getRegionCode());
                                        tradeStats.setTradeType(TradeType.TRADE);
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
    public V1PageResponse<V1TradeResponse> getTradeList(String startDate,
                                                        String endDate,
                                                        String searchType,
                                                        String regionName,
                                                        String regionCode,
                                                        String sortType,
                                                        String orderType,
                                                        String areaType,
                                                        String isNewData,
                                                        String tradeType,
                                                        String pageNo,
                                                        String size) {
        if(Integer.valueOf(size) > 100) {
            size = "100";
        }
        TradeType type = TradeType.fromString(tradeType);

        if (type == TradeType.RENT || type == TradeType.MONTHLY_RENT || type == TradeType.JEONSE) {
            return this.getRent(startDate,
                                endDate,
                                searchType,
                                regionName,
                                regionCode,
                                sortType,
                                orderType,
                                areaType,
                                isNewData,
                                type,
                                pageNo,
                                size);
        } else if (type == TradeType.TICKET) {
            return this.getTicket(startDate, endDate, searchType, regionName, regionCode, sortType, orderType, areaType, isNewData, pageNo, size);
        } else if (type == TradeType.TRADE) {
            return this.getTrade(startDate, endDate, searchType, regionName, regionCode, sortType, orderType, areaType, isNewData, pageNo, size);
        }
        throw new IllegalArgumentException("Unknown tradeType : " + tradeType);
    }

    @Override
    public List<SummaryResponse> getSummary(String startDate, String endDate, String regionCode) {
        Region region = regionRepository.findByCode(regionCode);
        RegionType regionType = region.getType();
        List<SummaryResponse> result = Lists.newArrayList();
        if (regionType == RegionType.SIDO) {
        } else if (regionType == RegionType.GUNGU) {
        } else if (regionType == RegionType.DONG) {
        }
        return result;
    }

    @Override
    public V1DetailResponse getDetail(String regionCode, String name, String range) {
        List<Apartment> apartmentList = apartmentRepository.findByDongCodeAndName(regionCode, name);
        Region region = regionRepository.findByCode(regionCode);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDate = now.minusYears(Long.parseLong(range)).format(formatter);
        String endDate = now.format(formatter);
        List<Trade> tradeList = tradeRepository.findByDateBetweenAndDongCodeAndName(startDate, endDate, regionCode, name);
        List<Ticket> ticketList = ticketRepository.findByDateBetweenAndDongCodeAndName(startDate, endDate, regionCode, name);
        List<Rent> rentList = rentRepository.findByDateBetweenAndDongCodeAndName(startDate, endDate, regionCode, name);

        V1DetailResponse v1DetailResponse = new V1DetailResponse();
        v1DetailResponse.setName(name);
        v1DetailResponse.setRegionCode(region.getCode());
        v1DetailResponse.setRegionName(region.getFullName());
        v1DetailResponse.setTypeList(apartmentList);
        v1DetailResponse.setTradeList(tradeList);
        v1DetailResponse.setTicketList(ticketList);
        v1DetailResponse.setRentList(rentList);
        return v1DetailResponse;
    }

    private V1PageResponse<V1TradeResponse> getRent(String startDate,
                                                    String endDate,
                                                    String searchType,
                                                    String regionName,
                                                    String regionCode,
                                                    String sortType,
                                                    String orderType,
                                                    String areaType,
                                                    String isNewData,
                                                    TradeType type,
                                                    String pageNo,
                                                    String size) {
        RegionType searchRegionType = RegionType.fromString(searchType);

        // SortType
        Sort.Direction direction = orderType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // AreaType
        AreaType area = AreaType.fromCode(areaType);
        Double startArea = area.getStartArea();
        Double endArea = area.getEndArea();

        PageRequest pageRequest = new PageRequest(Integer.valueOf(pageNo),
                                                  Integer.valueOf(size),
                                                  new Sort(direction, sortType.equals("regionType") ? "dongName" : sortType));

        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date baseTime = null;
        try {
            baseTime = justDay.parse(justDay.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            isNewData = "false";
        }

        Page<Rent> rentPage;
        if (type == TradeType.RENT) {
            rentPage = getRentsAll
                (startDate, endDate, regionName, regionCode, isNewData, searchRegionType, startArea, endArea, pageRequest, baseTime);
        } else if (type == TradeType.MONTHLY_RENT) {
            rentPage = getMonthlyRent
                (startDate, endDate, regionName, regionCode, isNewData, searchRegionType, startArea, endArea, pageRequest, baseTime);
        } else if (type == TradeType.JEONSE) {
            rentPage = getJeonse
                (startDate, endDate, regionName, regionCode, isNewData, searchRegionType, startArea, endArea, pageRequest, baseTime);
        } else {
            throw new IllegalArgumentException();
        }
        V1PageResponse<V1TradeResponse> result = new V1PageResponse<>();
        result.setPageNumber(rentPage.getNumber());
        result.setTotalElements(rentPage.getTotalElements());
        result.setTotalPages(rentPage.getTotalPages());
        result.setContents(rentPage.getContent()
                                   .stream()
                                   .filter(x -> x.getTradeType() == TradeType.RENT)
                                   .map(this::transform)
                                   .collect(Collectors.toList()));
        return result;
    }

    private Page<Rent> getJeonse(String startDate,
                                 String endDate,
                                 String regionName,
                                 String regionCode,
                                 String isNewData,
                                 RegionType searchRegionType,
                                 Double startArea,
                                 Double endArea,
                                 PageRequest pageRequest,
                                 Date baseTime) {
        if (searchRegionType == RegionType.APT) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPrice
                    (baseTime, startDate, endDate, regionCode, regionName, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPrice
                    (startDate, endDate, regionCode, regionName, startArea, endArea, 0, pageRequest);
            }
        } else if (searchRegionType == RegionType.SIDO) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetweenAndSubPrice
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndSidoCodeAndAreaBetweenAndSubPrice
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        } else if (searchRegionType == RegionType.GUNGU) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetweenAndSubPrice
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndGunguCodeAndAreaBetweenAndSubPrice
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        } else {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetweenAndSubPrice
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndAreaBetweenAndSubPrice
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        }
    }

    private Page<Rent> getMonthlyRent(String startDate,
                                      String endDate,
                                      String regionName,
                                      String regionCode,
                                      String isNewData,
                                      RegionType searchRegionType,
                                      Double startArea,
                                      Double endArea,
                                      PageRequest pageRequest,
                                      Date baseTime) {
        if (searchRegionType == RegionType.APT) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPriceIsNot
                    (baseTime, startDate, endDate, regionCode, regionName, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPriceIsNot
                    (startDate, endDate, regionCode, regionName, startArea, endArea, 0, pageRequest);
            }
        } else if (searchRegionType == RegionType.SIDO) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetweenAndSubPriceIsNot
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndSidoCodeAndAreaBetweenAndSubPriceIsNot
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        } else if (searchRegionType == RegionType.GUNGU) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetweenAndSubPriceIsNot
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndGunguCodeAndAreaBetweenAndSubPriceIsNot
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        } else {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetweenAndSubPriceIsNot
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndAreaBetweenAndSubPriceIsNot
                    (startDate, endDate, regionCode, startArea, endArea, 0, pageRequest);
            }
        }
    }

    private Page<Rent> getRentsAll(String startDate,
                                   String endDate,
                                   String regionName,
                                   String regionCode,
                                   String isNewData,
                                   RegionType searchRegionType, Double startArea, Double endArea, PageRequest pageRequest, Date baseTime) {
        if (searchRegionType == RegionType.APT) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween
                    (baseTime, startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndNameAndAreaBetween
                    (startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest);
            }
        } else if (searchRegionType == RegionType.SIDO) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndSidoCodeAndAreaBetween
                    (startDate, endDate, regionCode, startArea, endArea, pageRequest);
            }
        } else if (searchRegionType == RegionType.GUNGU) {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndGunguCodeAndAreaBetween
                    (startDate, endDate, regionCode, startArea, endArea, pageRequest);
            }
        } else {
            if (isNewData.equals("true")) {
                return rentRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween
                    (baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest);
            } else {
                return rentRepository.findByDateBetweenAndDongCodeAndAreaBetween
                    (startDate, endDate, regionCode, startArea, endArea, pageRequest);
            }
        }
    }

    private V1PageResponse<V1TradeResponse> getTicket(String startDate,
                                                      String endDate,
                                                      String searchType,
                                                      String regionName,
                                                      String regionCode,
                                                      String sortType,
                                                      String orderType,
                                                      String areaType,
                                                      String isNewData,
                                                      String pageNo,
                                                      String size) {
        Region region = regionRepository.findByCode(regionCode);
        RegionType regionType = region.getType();

        // SortType
        Sort.Direction direction;
        direction = orderType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // AreaType
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

        Page<Ticket> ticketPage;
        PageRequest pageRequest = new PageRequest(Integer.valueOf(pageNo), Integer.valueOf(size), new Sort(direction, sortType.equals("regionType") ? "dongName" : sortType));

        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date baseTime = null;
        try {
            baseTime = justDay.parse(justDay.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            isNewData = "false";
        }

        if("apt".equals(searchType)) {
            if(isNewData.equals("true")) {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndName(baseTime, startDate, endDate, regionCode, regionName, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, regionName, endArea, pageRequest)
                    : ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween(baseTime, startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest));
            }
            else {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByDateBetweenAndDongCodeAndName(startDate, endDate, regionCode, regionName, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(startDate, endDate, regionCode, regionName, endArea, pageRequest)
                    : ticketRepository.findByDateBetweenAndDongCodeAndNameAndAreaBetween(startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest));
            }
        }
        else if (regionType == RegionType.SIDO) {
            if(isNewData.equals("true")) {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByDateBetweenAndSidoCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByDateBetweenAndSidoCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByDateBetweenAndSidoCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        } else if (regionType == RegionType.GUNGU) {
            if(isNewData.equals("true")) {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByDateBetweenAndGunguCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByDateBetweenAndGunguCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByDateBetweenAndGunguCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        } else {
            if(isNewData.equals("true")) {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                ticketPage = areaType.equals("0") ?
                    ticketRepository.findByDateBetweenAndDongCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    ticketRepository.findByDateBetweenAndDongCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : ticketRepository.findByDateBetweenAndDongCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        }
        V1PageResponse<V1TradeResponse> result = new V1PageResponse<>();
        result.setPageNumber(ticketPage.getNumber());
        result.setTotalElements(ticketPage.getTotalElements());
        result.setTotalPages(ticketPage.getTotalPages());
        result.setContents(ticketPage.getContent()
                                    .stream()
                                    .filter(x -> x.getTradeType() == TradeType.TICKET)
                                    .map(this::transform)
                                    .collect(Collectors.toList()));
        return result;
    }

    private V1PageResponse<V1TradeResponse> getTrade(String startDate,
                                                     String endDate,
                                                     String searchType,
                                                     String regionName,
                                                     String regionCode,
                                                     String sortType,
                                                     String orderType,
                                                     String areaType,
                                                     String isNewData,
                                                     String pageNo,
                                                     String size) {
        Region region = regionRepository.findByCode(regionCode);
        RegionType regionType = region.getType();

        // SortType
        Sort.Direction direction;
        direction = orderType.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // AreaType
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

        Page<Trade> tradePage;
        PageRequest pageRequest = new PageRequest(Integer.valueOf(pageNo), Integer.valueOf(size), new Sort(direction, sortType.equals("regionType") ? "dongName" : sortType));

        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date baseTime = null;
        try {
            baseTime = justDay.parse(justDay.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            isNewData = "false";
        }

        if("apt".equals(searchType)) {
            if(isNewData.equals("true")) {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndName(baseTime, startDate, endDate, regionCode, regionName, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, regionName, endArea, pageRequest)
                    : tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween(baseTime, startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest));
            }
            else {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByDateBetweenAndDongCodeAndName(startDate, endDate, regionCode, regionName, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(startDate, endDate, regionCode, regionName, endArea, pageRequest)
                    : tradeRepository.findByDateBetweenAndDongCodeAndNameAndAreaBetween(startDate, endDate, regionCode, regionName, startArea, endArea, pageRequest));
            }
        }
        else if (regionType == RegionType.SIDO) {
            if(isNewData.equals("true")) {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByDateBetweenAndSidoCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByDateBetweenAndSidoCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByDateBetweenAndSidoCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        } else if (regionType == RegionType.GUNGU) {
            if(isNewData.equals("true")) {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByDateBetweenAndGunguCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByDateBetweenAndGunguCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByDateBetweenAndGunguCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        } else {
            if(isNewData.equals("true")) {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCode(baseTime, startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaGreaterThanEqual(baseTime, startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween(baseTime, startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
            else {
                tradePage = areaType.equals("0") ?
                    tradeRepository.findByDateBetweenAndDongCode(startDate, endDate, regionCode, pageRequest)
                    : (areaType.equals("5") ?
                    tradeRepository.findByDateBetweenAndDongCodeAndAreaGreaterThanEqual(startDate, endDate, regionCode, endArea, pageRequest)
                    : tradeRepository.findByDateBetweenAndDongCodeAndAreaBetween(startDate, endDate, regionCode, startArea, endArea, pageRequest));
            }
        }
        V1PageResponse<V1TradeResponse> result = new V1PageResponse<>();
        result.setPageNumber(tradePage.getNumber());
        result.setTotalElements(tradePage.getTotalElements());
        result.setTotalPages(tradePage.getTotalPages());
        result.setContents(tradePage.getContent()
                                    .stream()
                                    .filter(x -> x.getTradeType() == TradeType.TRADE)
                                    .map(this::transform)
                                    .collect(Collectors.toList()));
        return result;
    }


    private V1TradeResponse transform(Rent rent) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setSince(rent.getSince());
        v1TradeResponse.setDate(rent.getDate());
        v1TradeResponse.setDateName(rent.getDay());
        v1TradeResponse.setRegionCode(rent.getDongCode());
        v1TradeResponse.setRegionName(rent.getDongName());
        v1TradeResponse.setName(rent.getName());
        v1TradeResponse.setFloor(String.valueOf(rent.getFloor()));
        v1TradeResponse.setArea(String.valueOf(rent.getArea()));
        v1TradeResponse.setPrice(rent.getMainPrice());
        v1TradeResponse.setSubPrice(rent.getSubPrice());
        v1TradeResponse.setPastMaxPrice(0);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime baseTime = LocalDateTime.of(currentTime.getYear(),
                                                  currentTime.getMonthValue(),
                                                  currentTime.getDayOfMonth(),
                                                  5,
                                                  59,
                                                  59);
        LocalDateTime createdDateTime = rent.getCreatedDateTime();
        boolean isNewData = createdDateTime.isAfter(baseTime);
        v1TradeResponse.setIsNewData(isNewData);
        return v1TradeResponse;
    }

    private V1TradeResponse transform(Ticket ticket) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setSince("-");
        v1TradeResponse.setDate(ticket.getDate());
        v1TradeResponse.setDateName(ticket.getDay());
        v1TradeResponse.setRegionCode(ticket.getDongCode());
        v1TradeResponse.setRegionName(ticket.getDongName());
        v1TradeResponse.setName(ticket.getName());
        v1TradeResponse.setFloor(String.valueOf(ticket.getFloor()));
        v1TradeResponse.setArea(String.valueOf(ticket.getArea()));
        v1TradeResponse.setPrice(ticket.getMainPrice());
        v1TradeResponse.setSubPrice(ticket.getSubPrice());

/*
        Apartment aptMaxPrice = apartmentRepository.findByDongCodeAndNameAndArea(ticket.getDongCode(),
                                                                                         ticket.getName(),
                                                                                         ticket.getArea());
        if(ObjectUtils.isEmpty(aptMaxPrice)) {
            Apartment newAptMaxPrice = new Apartment();
            newAptMaxPrice.setName(ticket.getName());
            newAptMaxPrice.setDongCode(ticket.getDongCode());
            newAptMaxPrice.setGunguCode(ticket.getGunguCode());
            newAptMaxPrice.setArea(ticket.getArea());
            newAptMaxPrice.setSince("0");
            newAptMaxPrice.setMaxTicketPrice(ticket.getMainPrice());
            apartmentRepository.save(newAptMaxPrice);
            aptMaxPrice = newAptMaxPrice;
        }

        Integer max = Optional.ofNullable(aptMaxPrice.getMaxTicketPrice()).orElse(0);

        if(max == 0 || Objects.equals(ticket.getMainPrice(), max)) {
            log.debug("# {} # maxprice zero, {}", ticket.toString());
            YearMonth yearMonth = YearMonth.now();
            String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).replace("-", "");
            List<Ticket> pastTradeList = ticketRepository.findByDateLessThanEqualAndDongCodeAndNameAndAreaOrderByMainPrice(baseDate,
                                                                                                                          ticket
                                                                                                                              .getDongCode(),
                                                                                                                          ticket.getName(),
                                                                                                                          ticket.getArea());
            max = pastTradeList.stream()
                               .filter(x -> !Objects.equals(x.getOpenApiTicketInfoId(), ticket.getOpenApiTicketInfoId()))
                               .map(Ticket::getMainPrice)
                               .max(Integer::compareTo)
                               .orElse(0);
        }
*/
        v1TradeResponse.setPastMaxPrice(ticket.getMaxPrice());
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
        return v1TradeResponse;
    }

    private V1TradeResponse transform(Trade trade) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setSince(trade.getSince());
        v1TradeResponse.setDate(trade.getDate());
        v1TradeResponse.setDateName(trade.getDay());
        v1TradeResponse.setRegionCode(trade.getDongCode());
        v1TradeResponse.setRegionName(trade.getDongName());
        v1TradeResponse.setName(trade.getName());
        v1TradeResponse.setFloor(String.valueOf(trade.getFloor()));
        v1TradeResponse.setArea(String.valueOf(trade.getArea()));
        v1TradeResponse.setPrice(trade.getMainPrice());
        v1TradeResponse.setSubPrice(trade.getSubPrice());
        /*YearMonth yearMonth = YearMonth.parse(trade.getDate(), DateTimeFormatter.ofPattern("yyyyMM"));
        String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        String pastDate = yearMonth.minusYears(1)
                                   .format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        List<Trade> pastTradeList = tradeRepository.findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(pastDate,
                                                                                                               baseDate,
                                                                                                               trade.getDongCode(),
                                                                                                               trade.getName(),
                                                                                                               trade.getArea());
        Integer max = pastTradeList.stream()
                                   .filter(x -> !Objects.equals(x.getOpenApiTradeInfoId(), trade.getOpenApiTradeInfoId()))
                                   .map(Trade::getMainPrice)
                                   .max(Integer::compareTo)
                                   .orElse(0);*/
/*
        Apartment aptMaxPrice = apartmentRepository.findByDongCodeAndNameAndArea(trade.getDongCode(),
                                                                                     trade.getName(),
                                                                                     trade.getArea());
        if(ObjectUtils.isEmpty(aptMaxPrice)) {
            Apartment newAptMaxPrice = new Apartment();
            newAptMaxPrice.setName(trade.getName());
            newAptMaxPrice.setDongCode(trade.getDongCode());
            newAptMaxPrice.setGunguCode(trade.getGunguCode());
            newAptMaxPrice.setArea(trade.getArea());
            newAptMaxPrice.setSince(trade.getSince());
            newAptMaxPrice.setMaxTradePrice(trade.getMainPrice());
            apartmentRepository.save(newAptMaxPrice);
            aptMaxPrice = newAptMaxPrice;
        }

        Integer max = Optional.ofNullable(aptMaxPrice.getMaxTradePrice()).orElse(0);

        if(max == 0 || Objects.equals(trade.getMainPrice(), max)) {
            log.debug("# {} # maxprice zero, {}", trade.toString());
            YearMonth yearMonth = YearMonth.now();
            String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).replace("-", "");
            String pastDate = trade.getSince() + "01";
            List<Trade> pastTradeList = tradeRepository.findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(pastDate,
                                                                                                                   baseDate,
                                                                                                                   trade.getDongCode(),
                                                                                                                   trade.getName(),
                                                                                                                   trade.getArea());
            max = pastTradeList.stream()
                                       .filter(x -> !Objects.equals(x.getOpenApiTradeInfoId(), trade.getOpenApiTradeInfoId()))
                                       .map(Trade::getMainPrice)
                                       .max(Integer::compareTo)
                                       .orElse(0);
        }
*/
        v1TradeResponse.setPastMaxPrice(trade.getMaxPrice());
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime baseTime = LocalDateTime.of(currentTime.getYear(),
                                                  currentTime.getMonthValue(),
                                                  currentTime.getDayOfMonth(),
                                                  5,
                                                  59,
                                                  59);
        LocalDateTime createdDateTime = trade.getCreatedDateTime();
        boolean isNewData = createdDateTime.isAfter(baseTime);
        v1TradeResponse.setIsNewData(isNewData);
        return v1TradeResponse;
    }

    private V1MaxTradeResponse transform(Trade trade, Integer range) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setSince(trade.getSince());
        v1TradeResponse.setDate(trade.getDate());
        v1TradeResponse.setDateName(trade.getDay());
        v1TradeResponse.setRegionCode(trade.getDongCode());
        v1TradeResponse.setRegionName(trade.getDongName());
        v1TradeResponse.setName(trade.getName());
        v1TradeResponse.setFloor(String.valueOf(trade.getFloor()));
        v1TradeResponse.setArea(String.valueOf(trade.getArea()));
        v1TradeResponse.setPrice(trade.getMainPrice());
        YearMonth yearMonth = YearMonth.parse(trade.getDate(), DateTimeFormatter.ofPattern("yyyyMM"));
        String baseDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        String pastDate = yearMonth.minusYears(range)
                                   .format(DateTimeFormatter.ofPattern("yyyyMM"))
                                   .replace("-", "");
        List<Trade> pastTradeList = tradeRepository.findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(pastDate,
                                                                                                               baseDate,
                                                                                                               trade.getDongCode(),
                                                                                                               trade.getName(),
                                                                                                               trade.getArea());
        Integer max = pastTradeList.stream()
                                   .filter(x -> !Objects.equals(x.getOpenApiTradeInfoId(), trade.getOpenApiTradeInfoId()))
                                   .map(Trade::getMainPrice)
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
        LocalDateTime createdDateTime = trade.getCreatedDateTime();
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

    private V1TradeResponse getSimpleData(Trade trade) {
        V1TradeResponse v1TradeResponse = new V1TradeResponse();
        v1TradeResponse.setSince(trade.getSince());
        v1TradeResponse.setDate(trade.getDate());
        v1TradeResponse.setDateName(trade.getDay());
        v1TradeResponse.setName(trade.getName());
        v1TradeResponse.setFloor(String.valueOf(trade.getFloor()));
/*
        v1TradeResponse.setArea(trade.getArea());
*/
        v1TradeResponse.setPrice(trade.getMainPrice());
        return v1TradeResponse;
    }
}
