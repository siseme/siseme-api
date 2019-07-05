package me.sise.api.interfaces.v2.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentRankResponse {
    private String regionType;
    private String regionName;
    private String fromDate;
    private String toDate;

    private List<RentCountRank> rentCountRanks;

    private RentRankResponse() {

    }

    public static Builder builder() {
        return new Builder();
    }

    @Data
    public static class RentCountRank {
        private String regionType;
        private String regionCode;
        private String regionName;
        private long count;
        private int ranking;
    }

    public static final class Builder {
        private String regionType;
        private String regionName;
        private String fromDate;
        private String toDate;
        private List<RentCountRank> rentCountRanks;

        private Builder() {}

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

        public Builder withRentCountRanks(List<RentCountRank> rentCountRanks) {
            this.rentCountRanks = rentCountRanks;
            return this;
        }

        public RentRankResponse build() {
            RentRankResponse rentRankResponse = new RentRankResponse();
            rentRankResponse.regionName = this.regionName;
            rentRankResponse.fromDate = this.fromDate;
            rentRankResponse.regionType = this.regionType;
            rentRankResponse.rentCountRanks = this.rentCountRanks;
            rentRankResponse.toDate = this.toDate;
            return rentRankResponse;
        }
    }
}
