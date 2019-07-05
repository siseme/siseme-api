package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.OpenApiTradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenApiTradeInfoRepository extends JpaRepository<OpenApiTradeInfo, Long> {
    Long countByYearAndMonth(String year, String month);

    List<OpenApiTradeInfo> findByYearAndMonth(String year, String month);

    List<OpenApiTradeInfo> findByRegionCode(String regionCode);
}
