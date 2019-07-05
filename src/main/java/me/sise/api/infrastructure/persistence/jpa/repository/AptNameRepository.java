package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.AptName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AptNameRepository extends JpaRepository<AptName, Long> {
    List<AptName> findByDongCode(String dongCode);
    List<AptName> findByName(String name);
    AptName findByDongCodeAndName(String dongCode, String name);
}
