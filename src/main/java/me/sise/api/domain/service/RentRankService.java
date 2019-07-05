package me.sise.api.domain.service;

import me.sise.api.interfaces.v2.dto.RentRankResponse;

public interface RentRankService {
    RentRankResponse getRentRank(String startDate, String endDate, String regionType, String regionCode);
}
