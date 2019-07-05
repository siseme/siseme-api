package me.sise.api.domain.service;

import me.sise.api.domain.dto.BubbleIndexModel;

public interface RegionStatsService {
    BubbleIndexModel getBubbleIndex(String regionCode, String startDate, String endDate);
}
