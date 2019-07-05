package me.sise.api.domain.service;

import me.sise.api.interfaces.v1.dto.response.V1MaxTradeResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1StatsResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeCountResponse;

import java.util.List;

public interface TicketService {
    V1PageResponse<V1MaxTradeResponse> getMaxTradeListByPaging(String date,
                                                               String regionCode,
                                                               String searchType,
                                                               String pageNo,
                                                               String size,
                                                               String sortType,
                                                               String areaType,
                                                               String rangeType);
    V1StatsResponse getStats(String regionCode, String date, String rangeType);
    List<V1TradeCountResponse> getTradeCount(String regionCode, String date, String rangeType);
}
