package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.MainStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainStatsRepository extends JpaRepository<MainStats, Long> {
    MainStats findByDateAndRegionCode(String date, String regionCode);
    MainStats findByDate(String date);
}
