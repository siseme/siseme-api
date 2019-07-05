package me.sise.api.interfaces.v2.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeType;

import java.util.List;

@Data
public class StatsResponse {
    private List<Stats> type1TradeStatsList = Lists.newArrayList();
    private List<Stats> type2TradeStatsList = Lists.newArrayList();
    private List<Stats> type3TradeStatsList = Lists.newArrayList();
    private List<Stats> type4TradeStatsList = Lists.newArrayList();
    private List<Stats> type5TradeStatsList = Lists.newArrayList();

    @Data
    public static class Stats {
        private String date;
        private String regionCode;
        private Integer sumMainPrice;
        private Integer count;
        private String areaType;
        private TradeType tradeType;
    }
}
