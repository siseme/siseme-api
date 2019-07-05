package me.sise.api.interfaces.v1.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class V1MaxTradeResponse {
    private V1TradeResponse trade;
    private List<V1TradeResponse> pastTradeList;
}
