package me.sise.api.domain.service;

import me.sise.api.interfaces.v1.dto.response.V1DetailResponse;
import me.sise.api.interfaces.v1.dto.response.V1MaxTradeResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1StatsResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeCountResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeResponse;
import me.sise.api.interfaces.v2.dto.DealsCountResponse;
import me.sise.api.interfaces.v2.dto.StatsResponse;
import me.sise.api.interfaces.v2.dto.SummaryResponse;

import java.util.List;

public interface TradeService {
    /*
        List<V1MaxTradeResponse> getMaxTradeList(String date, String regionCode, String searchType);
    */
    V1PageResponse<V1MaxTradeResponse> getMaxTradeListByPaging(String date,
                                                               String regionCode,
                                                               String searchType,
                                                               String pageNo,
                                                               String size,
                                                               String sortType,
                                                               String areaType,
                                                               String rangeType);

    V1StatsResponse getStats(String regionCode, String date, String rangeType);

    StatsResponse getStatsMap(String tradeType, String regionCode, String date, String rangeType);

    List<V1TradeCountResponse> getTradeCount(String regionCode, String date, String rangeType);

    V1PageResponse<V1TradeResponse> getTradeList(String startDate,
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
                                                 String size);

    List<SummaryResponse> getSummary(String startDate, String endDate, String regionCode);

    V1DetailResponse getDetail(String regionCode, String name, String range);

    DealsCountResponse getCount(String startDate,
                                String endDate,
                                String regionCode,
                                String tradeType,
                                String searchType);
}
