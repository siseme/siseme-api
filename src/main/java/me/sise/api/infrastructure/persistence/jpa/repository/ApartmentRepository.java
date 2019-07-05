package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByDongCode(String code);

    List<Apartment> findByDongCodeAndName(String dongCode, String name);

    Apartment findByDongCodeAndNameAndArea(String dongCode, String name, Double area);

    @Query(value = "SELECT count(*) FROM apartment t WHERE t.updated_date >= ?1", nativeQuery = true)
    Long countByUpdatedDateGreaterThanEqual(Date parse);
}
