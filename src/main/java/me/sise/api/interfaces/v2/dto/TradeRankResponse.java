package me.sise.api.interfaces.v2.dto;

import lombok.Data;

import java.util.List;

@Data
public class TradeRankResponse {

    private String regionType;
    private String regionName;
    private String fromDate;
    private String toDate;

    private List<NumberOfTradeRank> numberOfTradeRanks;
    private List<NumberOfNewHighPriceRank> numberOfNewHighPriceRanks;
    private List<UnitPriceRank> unitPriceRanks;

    public static Builder builder() {
        return new Builder();
    }

    @Data
    public static class NumberOfTradeRank {
        private String regionType;
        private String regionCode;
        private String regionName;
        private long count;
        private int ranking;
    }

    @Data
    public static class NumberOfNewHighPriceRank {
        private String regionType;
        private String regionCode;
        private String regionName;
        private long newHighPriceCount;
        private long totalTradeCount;
        private int ranking;
    }

    @Data
    public static class UnitPriceRank {
        private String regionType;
        private String regionCode;
        private String regionName;
        private double unitPrice;
        private int ranking;
    }

    public static final class Builder {
        private String regionType;
        private String regionName;
        private String fromDate;
        private String toDate;

        private List<NumberOfTradeRank> numberOfTradeRanks;
        private List<NumberOfNewHighPriceRank> numberOfNewHighPriceRanks;
        private List<UnitPriceRank> unitPriceRanks;

        private Builder() { }

        public Builder withRegionType(String regionType) {
            this.regionType = regionType;
            return this;
        }

        public Builder withRegionName(String regionName) {
            this.regionName = regionName;
            return this;
        }

        public Builder withFromDate(String fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder withToDate(String toDate) {
            this.toDate = toDate;
            return this;
        }

        public Builder withNumberOfTradeRanks(List<NumberOfTradeRank> numberOfTradeRanks) {
            this.numberOfTradeRanks = numberOfTradeRanks;
            return this;
        }

        public Builder withNumberOfNewHighPriceRanks(List<NumberOfNewHighPriceRank> numberOfNewHighPriceRanks) {
            this.numberOfNewHighPriceRanks = numberOfNewHighPriceRanks;
            return this;
        }

        public Builder withUnitPriceRanks(List<UnitPriceRank> unitPriceRanks) {
            this.unitPriceRanks = unitPriceRanks;
            return this;
        }

        public TradeRankResponse build() {
            TradeRankResponse rankModel = new TradeRankResponse();
            rankModel.setRegionType(regionType);
            rankModel.setRegionName(regionName);
            rankModel.setFromDate(fromDate);
            rankModel.setToDate(toDate);
            rankModel.setNumberOfTradeRanks(numberOfTradeRanks);
            rankModel.setNumberOfNewHighPriceRanks(numberOfNewHighPriceRanks);
            rankModel.setUnitPriceRanks(unitPriceRanks);
            return rankModel;
        }
    }
}
