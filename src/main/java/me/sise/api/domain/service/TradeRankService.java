package me.sise.api.domain.service;

import me.sise.api.interfaces.v2.dto.TradeRankResponse;

public interface TradeRankService {

    TradeRankResponse getTradeRank(String startDate,
                                   String endDate,
                                   String regionType,
                                   String regionCode);
}
