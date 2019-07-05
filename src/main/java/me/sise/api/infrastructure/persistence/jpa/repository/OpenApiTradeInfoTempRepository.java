package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.OpenApiTradeInfoTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenApiTradeInfoTempRepository extends JpaRepository<OpenApiTradeInfoTemp, Long> {

    Page<OpenApiTradeInfoTemp> findByIsChecked(Boolean isChecked, Pageable pageable);

    Long countByIsChecked(Boolean isChecked);
}
