package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.OpenApiRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenApiRentInfoRepository extends JpaRepository<OpenApiRentInfo, Long> {
    List<OpenApiRentInfo> findByYearAndMonth(String year, String month);
}
