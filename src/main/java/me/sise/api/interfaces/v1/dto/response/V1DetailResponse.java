package me.sise.api.interfaces.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import me.sise.api.infrastructure.persistence.jpa.entity.Apartment;
import me.sise.api.infrastructure.persistence.jpa.entity.Rent;
import me.sise.api.infrastructure.persistence.jpa.entity.Ticket;
import me.sise.api.infrastructure.persistence.jpa.entity.Trade;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class V1DetailResponse implements Serializable {
    private static final long serialVersionUID = -6345708666415461763L;
    private String regionCode;
    private String regionName;
    private String name;
    private List<Apartment> typeList;
    private List<Trade> tradeList;
    private List<Ticket> ticketList;
    private List<Rent> rentList;
}
