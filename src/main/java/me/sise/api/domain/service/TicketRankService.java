package me.sise.api.domain.service;

import me.sise.api.interfaces.v2.dto.TicketRankResponse;

public interface TicketRankService {
    TicketRankResponse getTicketRank(String startDate, String endDate, String regionType, String regionCode);
}
