package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.TicketRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRankRepository extends JpaRepository<TicketRank, Long> {

    List<TicketRank> findByRegionAndDate(Region region, String yyyyMmDate);

    @Query(
        "SELECT new me.sise.api.infrastructure.persistence.jpa.repository.AggregateCount(r.regionCode, r.regionName, r.regionType, " +
            "sum(r.count)) " +
            "FROM TicketRank r " +
            "WHERE r.region=:region and r.date >= :startDate and r.date <= :endDate " +
            "GROUP BY r.regionCode, r.regionName, r.regionType")
    List<AggregateCount> findAggregateCountBetweenDate(@Param("region") Region region,
                                                       @Param("startDate") String startDate,
                                                       @Param("endDate") String endDate);

}
