package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionStatsRepository extends JpaRepository<RegionStats, Long> {

    @Query(
        value = "select * from region_stats, region " +
            "where region_stats.region_id = region.id " +
            "and region.code = :regionCode " +
            "and region_stats.date = :date",
        nativeQuery = true)
    RegionStats findByRegionCodeAndDate(@Param("regionCode") String regionCode,
                                        @Param("date") String date);

    List<RegionStats> findByRegionAndDateIn(Region region,
                                            List<String> dates);

    @Query(
        value = "select new me.sise.api.infrastructure.persistence.jpa.entity.RegionStats(rs.date, " +
            "avg(rs.averagePriceOfTrade), avg(rs.averagePriceOfJeonse)) " +
            "from RegionStats rs join rs.region r " +
            "where r.type = me.sise.api.infrastructure.persistence.jpa.entity.RegionType.SIDO " +
            "and rs.date in (:dates) " +
            "group by rs.date")
    List<RegionStats> findNationalAverageByDates(@Param("dates") List<String> dates);
}
