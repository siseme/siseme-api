package me.sise.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TICKET", indexes = {@Index(columnList = "name"), @Index(columnList = "date"), @Index(columnList = "sidoCode"), @Index(columnList = "gunguCode"), @Index(columnList = "dongCode")})
public class Ticket extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;
    @Enumerated(EnumType.ORDINAL)
    private MonthType monthType;
    private String date;
    private Double area;
    private Integer floor;
    private String name;
    private Integer mainPrice;
    private Integer subPrice;
    private Integer maxPrice;
    private String sidoCode;
    private String gunguCode;
    private String dongCode;
    private String dongName;
    private String lotNumber;
    private Long openApiTicketInfoId;
}
