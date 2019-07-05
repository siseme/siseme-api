package me.sise.api.interfaces.v1.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeStats;

import java.util.List;

@Data
public class V1StatsResponse {
    private List<TradeStats> type1TradeStatsList = Lists.newArrayList();
    private List<TradeStats> type2TradeStatsList = Lists.newArrayList();
    private List<TradeStats> type3TradeStatsList = Lists.newArrayList();
    private List<TradeStats> type4TradeStatsList = Lists.newArrayList();
    private List<TradeStats> type5TradeStatsList = Lists.newArrayList();
}
