package me.sise.api.interfaces.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class V1TradeResponse implements Serializable {
    private static final long serialVersionUID = 4912463973956614363L;
    private String since;
    private String date;
    private String dateName;
    private String regionCode;
    private String regionName;
    private String name;
    private String floor;
    private String area;
    private Integer price;
    private Integer subPrice;
    private Integer pastMaxPrice;
    private Boolean isNewData;
}
