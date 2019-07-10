package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByDate(String date);

    Page<Rent> findByDateBetweenAndDongCodeAndNameAndAreaBetween(String startDate,
                                                                 String endDate,
                                                                 String dongCode,
                                                                 String name,
                                                                 Double startArea,
                                                                 Double endArea,
                                                                 Pageable pageable);

    Page<Rent> findByDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPriceIsNot(String startDate,
                                                                                 String endDate,
                                                                                 String dongCode,
                                                                                 String name,
                                                                                 Double startArea,
                                                                                 Double endArea,
                                                                                 int subPrice,
                                                                                 Pageable pageable);

    Page<Rent> findByDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPrice(String startDate,
                                                                            String endDate,
                                                                            String dongCode,
                                                                            String name,
                                                                            Double startArea,
                                                                            Double endArea,
                                                                            int subPrice,
                                                                            Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween(Date updatedDate, String startDate,
                                                                                                   String endDate, String dongCode,
                                                                                                   String name, Double startArea,
                                                                                                   Double endArea, Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPriceIsNot(Date updatedDate,
                                                                                                                   String startDate,
                                                                                                                   String endDate,
                                                                                                                   String dongCode,
                                                                                                                   String name,
                                                                                                                   Double startArea,
                                                                                                                   Double endArea,
                                                                                                                   int subPrice,
                                                                                                                   Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetweenAndSubPrice(Date updatedDate,
                                                                                                              String startDate,
                                                                                                              String endDate,
                                                                                                              String dongCode,
                                                                                                              String name,
                                                                                                              Double startArea,
                                                                                                              Double endArea,
                                                                                                              int subPrice,
                                                                                                              Pageable pageable);

    Page<Rent> findByDateBetweenAndSidoCodeAndAreaBetween(String startDate, String endDate, String sidoCode, Double startArea,
                                                          Double endArea, Pageable pageable);

    Page<Rent> findByDateBetweenAndSidoCodeAndAreaBetweenAndSubPriceIsNot(String startDate,
                                                                          String endDate,
                                                                          String sidoCode,
                                                                          Double startArea,
                                                                          Double endArea,
                                                                          int subPrice,
                                                                          Pageable pageable);

    Page<Rent> findByDateBetweenAndSidoCodeAndAreaBetweenAndSubPrice(String startDate,
                                                                     String endDate,
                                                                     String sidoCode,
                                                                     Double startArea,
                                                                     Double endArea,
                                                                     int subPrice,
                                                                     Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween(Date updatedDate,
                                                                                            String startDate,
                                                                                            String endDate,
                                                                                            String sidoCode,
                                                                                            Double startArea,
                                                                                            Double endArea,
                                                                                            Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetweenAndSubPriceIsNot(Date updatedDate,
                                                                                                            String startDate,
                                                                                                            String endDate,
                                                                                                            String sidoCode,
                                                                                                            Double startArea,
                                                                                                            Double endArea,
                                                                                                            int subPrice,
                                                                                                            Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetweenAndSubPrice(Date updatedDate,
                                                                                                       String startDate,
                                                                                                       String endDate,
                                                                                                       String sidoCode,
                                                                                                       Double startArea,
                                                                                                       Double endArea,
                                                                                                       int subPrice,
                                                                                                       Pageable pageable);

    Page<Rent> findByDateBetweenAndGunguCodeAndAreaBetween(String startDate,
                                                           String endDate,
                                                           String gunguCode,
                                                           Double startArea,
                                                           Double endArea,
                                                           Pageable pageable);

    Page<Rent> findByDateBetweenAndGunguCodeAndAreaBetweenAndSubPriceIsNot(String startDate,
                                                                           String endDate,
                                                                           String gunguCode,
                                                                           Double startArea,
                                                                           Double endArea,
                                                                           int subPrice,
                                                                           Pageable pageable);

    Page<Rent> findByDateBetweenAndGunguCodeAndAreaBetweenAndSubPrice(String startDate,
                                                                      String endDate,
                                                                      String gunguCode,
                                                                      Double startArea,
                                                                      Double endArea,
                                                                      int subPrice,
                                                                      Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween(Date updatedDate,
                                                                                             String startDate,
                                                                                             String endDate,
                                                                                             String gunguCode,
                                                                                             Double startArea,
                                                                                             Double endArea,
                                                                                             Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetweenAndSubPriceIsNot(Date updatedDate,
                                                                                                             String startDate,
                                                                                                             String endDate,
                                                                                                             String gunguCode,
                                                                                                             Double startArea,
                                                                                                             Double endArea,
                                                                                                             int subPrice,
                                                                                                             Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetweenAndSubPrice(Date updatedDate,
                                                                                                        String startDate,
                                                                                                        String endDate,
                                                                                                        String gunguCode,
                                                                                                        Double startArea,
                                                                                                        Double endArea,
                                                                                                        int subPrice,
                                                                                                        Pageable pageable);

    Page<Rent> findByDateBetweenAndDongCodeAndAreaBetween(String startDate,
                                                          String endDate,
                                                          String dongCode,
                                                          Double startArea,
                                                          Double endArea,
                                                          Pageable pageable);

    Page<Rent> findByDateBetweenAndDongCodeAndAreaBetweenAndSubPriceIsNot(String startDate,
                                                                          String endDate,
                                                                          String dongCode,
                                                                          Double startArea,
                                                                          Double endArea,
                                                                          int subPrice,
                                                                          Pageable pageable);

    Page<Rent> findByDateBetweenAndDongCodeAndAreaBetweenAndSubPrice(String startDate,
                                                                     String endDate,
                                                                     String dongCode,
                                                                     Double startArea,
                                                                     Double endArea,
                                                                     int subPrice,
                                                                     Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween(Date updatedDate,
                                                                                            String startDate,
                                                                                            String endDate,
                                                                                            String dongCode,
                                                                                            Double startArea,
                                                                                            Double endArea,
                                                                                            Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetweenAndSubPriceIsNot(Date updatedDate,
                                                                                                            String startDate,
                                                                                                            String endDate,
                                                                                                            String dongCode,
                                                                                                            Double startArea,
                                                                                                            Double endArea,
                                                                                                            int subPrice,
                                                                                                            Pageable pageable);

    Page<Rent> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetweenAndSubPrice(Date updatedDate,
                                                                                                       String startDate,
                                                                                                       String endDate,
                                                                                                       String dongCode,
                                                                                                       Double startArea,
                                                                                                       Double endArea,
                                                                                                       int subPrice,
                                                                                                       Pageable pageable);

    @Query(value = "SELECT count(*) FROM rent t WHERE t.created_date >= ?1", nativeQuery = true)
    Long countByCreatedDateGreaterThanEqual(Date parse);

    List<Rent> findByDateAndSidoCode(String yyyyMmDate, String code);
    List<Rent> findByDateAndGunguCode(String yyyyMmDate, String code);
    List<Rent> findByDateAndDongCode(String yyyyMmDate, String code);
    List<Rent> findByDateBetweenAndDongCodeAndName(String startDate, String endDate, String dongCode, String name);

    @Query(value = "SELECT count(*) FROM rent WHERE (date BETWEEN ?1 AND ?2) AND sido_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndSidoCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String sidoCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM rent WHERE (date BETWEEN ?1 AND ?2) AND gungu_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndGunguCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String gunguCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM rent WHERE (date BETWEEN ?1 AND ?2) AND dong_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndDongCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String dongCode, Date createdDate);
}
