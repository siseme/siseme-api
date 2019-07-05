package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeRanks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRanksRepository extends JpaRepository<TradeRanks, Long> {
    List<TradeRanks> findByRegionAndDate(Region region, String date);

    List<TradeRanks> findByRegionAndDateBetween(Region region, String startDate, String endDate);
}
