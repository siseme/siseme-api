package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.RouteLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteLogRepository extends JpaRepository<RouteLog, Long> {
}
