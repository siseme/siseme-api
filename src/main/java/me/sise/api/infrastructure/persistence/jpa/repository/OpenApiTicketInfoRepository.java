package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.OpenApiTicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenApiTicketInfoRepository extends JpaRepository<OpenApiTicketInfo, Long> {
    List<OpenApiTicketInfo> findByYearAndMonth(String year, String month);
}
