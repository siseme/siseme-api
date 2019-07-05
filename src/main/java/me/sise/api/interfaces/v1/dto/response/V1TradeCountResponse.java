package me.sise.api.interfaces.v1.dto.response;

import lombok.Data;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeStats;

import java.util.List;

@Data
public class V1TradeCountResponse {
    private String name;
    private String code;
    private List<TradeStats> tradeStatsList;
}
