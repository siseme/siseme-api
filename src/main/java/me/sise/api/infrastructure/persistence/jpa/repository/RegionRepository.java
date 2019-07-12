package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByCodeLike(String code);

    List<Region> findByTypeOrderByNameAsc(RegionType type);

    List<Region> findByCodeLikeAndTypeOrderByNameAsc(String code, RegionType type);

    Region findByCode(String code);

    Region findByCodeAndType(String code, RegionType type);

    List<Region> findByType(RegionType type);

    List<Region> findByCodeLikeAndType(String code, RegionType type);

    List<Region> findByCodeLikeAndNameAndType(String code, String name, RegionType type);

    List<Region> findByTypeAndFullNameContaining(RegionType type, String fullName);

    Page<Region> findByTypeAndFullNameContaining(RegionType type, String fullName, Pageable pageable);

    Page<Region> findByFullNameContaining(String fullName, Pageable pageable);

    Region findByFullName(String fullName);
}
