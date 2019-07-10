package me.sise.api.interfaces.v2.dto;

import lombok.Data;

@Data
public class DealsCountResponse {
    private Long totalCount;
    private Long newItemCount;
    private Long maxPriceCount;
}
