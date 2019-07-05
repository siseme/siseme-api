package me.sise.api.interfaces.v2.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketRankResponse {
    private String regionType;
    private String regionName;
    private String fromDate;
    private String toDate;

    private List<TicketCountRank> ticketCountRanks;

    private TicketRankResponse() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Data
    public static class TicketCountRank {
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
        private List<TicketCountRank> ticketCountRanks;

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

        public Builder withTicketCountRanks(List<TicketCountRank> ticketCountRanks) {
            this.ticketCountRanks = ticketCountRanks;
            return this;
        }

        public TicketRankResponse build() {
            TicketRankResponse ticketRankResponse = new TicketRankResponse();
            ticketRankResponse.setRegionType(regionType);
            ticketRankResponse.setRegionName(regionName);
            ticketRankResponse.setFromDate(fromDate);
            ticketRankResponse.setToDate(toDate);
            ticketRankResponse.setTicketCountRanks(ticketCountRanks);
            return ticketRankResponse;
        }
    }
}