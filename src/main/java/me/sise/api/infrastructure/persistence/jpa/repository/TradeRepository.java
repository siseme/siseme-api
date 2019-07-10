package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByDateAndSidoCode(String date, String sidoCode);
    Page<Trade> findByDateAndSidoCode(String date, String regionCode, Pageable pageable);
    Page<Trade> findByDateAndSidoCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);
    Page<Trade> findByDateAndSidoCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Trade> findByDateAndGunguCode(String date, String gunguCode);
    Page<Trade> findByDateAndGunguCode(String date, String regionCode, Pageable pageable);
    Page<Trade> findByDateAndGunguCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);
    Page<Trade> findByDateAndGunguCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Trade> findByDateAndDongCode(String date, String dongCode);
    Page<Trade> findByDateAndDongCode(String date, String regionCode, Pageable pageable);
    Page<Trade> findByDateAndDongCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);
    Page<Trade> findByDateAndDongCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Trade> findByDate(String date);
    List<Trade> findByDateBetweenAndSidoCode(String startDate, String endDate, String sidoCode);

    @Query(value = "SELECT * FROM trade t WHERE t.date BETWEEN ?1 and ?2 and t.dong_code = ?3 and t.name = ?4 and t.area = ?5", nativeQuery = true)
    List<Trade> findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(String startDate,
                                                                           String endDate,
                                                                           String dongCode,
                                                                           String name,
                                                                           Double area);
    List<Trade> findByDateLessThanEqualAndDongCodeAndNameAndAreaOrderByMainPrice(String date, String dongCode, String name, Double area);
    List<Trade> findByDateLessThanEqualAndDongCodeAndNameAndArea(String date, String dongCode, String name, Double area);

    @Query(value = "SELECT IFNULL(MAX(t.main_price), 0) FROM trade t where t.date <= ?1 and t.dong_code = ?2 and t.name = ?3 and t.area =" +
        " ?4 and t.open_api_trade_info_id != ?5",
           nativeQuery = true)
    Integer findMaxPrice(String date, String dongCode, String name, Double area, Long openApiTradeInfoId);
    List<Trade> findByDateLessThanEqualAndDongCode(String date, String dongCode);
    List<Trade> findByDateBetweenAndDongCodeAndNameAndArea(String startDate, String endDate, String dongCode, String name, Double area);

    Page<Trade> findByDateBetweenAndDongCodeAndName(String startDate, String endDate, String dongCode, String name, Pageable pageable);
    Page<Trade> findByDateBetweenAndDongCodeAndNameAndAreaBetween(String startDate,
                                                                  String endDate,
                                                                  String dongCode,
                                                                  String name,
                                                                  Double startArea,
                                                                  Double endArea,
                                                                  Pageable pageable);
    Page<Trade> findByDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(String startDate,
                                                                           String endDate,
                                                                           String dongCode,
                                                                           String name,
                                                                           Double area,
                                                                           Pageable pageable);

    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndName(Date updatedDate,
                                                                                      String startDate,
                                                                                      String endDate,
                                                                                      String dongCode,
                                                                                      String name,
                                                                                      Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween(Date updatedDate,
                                                                                                    String startDate,
                                                                                                    String endDate,
                                                                                                    String dongCode,
                                                                                                    String name,
                                                                                                    Double startArea,
                                                                                                    Double endArea,
                                                                                                    Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                             String startDate,
                                                                                                             String endDate,
                                                                                                             String dongCode,
                                                                                                             String name,
                                                                                                             Double area,
                                                                                                             Pageable pageable);

    Page<Trade> findByDateBetweenAndSidoCode(String startDate, String endDate, String sidoCode, Pageable pageable);
    Page<Trade> findByDateBetweenAndSidoCodeAndAreaBetween(String startDate,
                                                           String endDate,
                                                           String sidoCode,
                                                           Double startArea,
                                                           Double endArea,
                                                           Pageable pageable);
    Page<Trade> findByDateBetweenAndSidoCodeAndAreaGreaterThanEqual(String startDate,
                                                                    String endDate,
                                                                    String sidoCode,
                                                                    Double area,
                                                                    Pageable pageable);

    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCode(Date updatedDate,
                                                                               String startDate,
                                                                               String endDate,
                                                                               String sidoCode,
                                                                               Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween(Date updatedDate,
                                                                                             String startDate,
                                                                                             String endDate,
                                                                                             String sidoCode,
                                                                                             Double startArea,
                                                                                             Double endArea,
                                                                                             Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                      String startDate,
                                                                                                      String endDate,
                                                                                                      String sidoCode,
                                                                                                      Double area,
                                                                                                      Pageable pageable);

    Page<Trade> findByDateBetweenAndGunguCode(String startDate, String endDate, String gunguCode, Pageable pageable);
    Page<Trade> findByDateBetweenAndGunguCodeAndAreaBetween(String startDate,
                                                            String endDate,
                                                            String gunguCode,
                                                            Double startArea,
                                                            Double endArea,
                                                            Pageable pageable);
    Page<Trade> findByDateBetweenAndGunguCodeAndAreaGreaterThanEqual(String startDate,
                                                                     String endDate,
                                                                     String gunguCode,
                                                                     Double area,
                                                                     Pageable pageable);

    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCode(Date updatedDate,
                                                                                String startDate,
                                                                                String endDate,
                                                                                String gunguCode,
                                                                                Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween(Date updatedDate,
                                                                                              String startDate,
                                                                                              String endDate,
                                                                                              String gunguCode,
                                                                                              Double startArea,
                                                                                              Double endArea,
                                                                                              Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                       String startDate,
                                                                                                       String endDate,
                                                                                                       String gunguCode,
                                                                                                       Double area,
                                                                                                       Pageable pageable);

    Page<Trade> findByDateBetweenAndDongCode(String startDate, String endDate, String dongCode, Pageable pageable);
    Page<Trade> findByDateBetweenAndDongCodeAndAreaBetween(String startDate,
                                                           String endDate,
                                                           String dongCode,
                                                           Double startArea,
                                                           Double endArea,
                                                           Pageable pageable);
    Page<Trade> findByDateBetweenAndDongCodeAndAreaGreaterThanEqual(String startDate,
                                                                    String endDate,
                                                                    String dongCode,
                                                                    Double area,
                                                                    Pageable pageable);

    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCode(Date updatedDate,
                                                                               String startDate,
                                                                               String endDate,
                                                                               String dongCode,
                                                                               Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween(Date updatedDate,
                                                                                             String startDate,
                                                                                             String endDate,
                                                                                             String dongCode,
                                                                                             Double startArea,
                                                                                             Double endArea,
                                                                                             Pageable pageable);
    Page<Trade> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                      String startDate,
                                                                                                      String endDate,
                                                                                                      String dongCode,
                                                                                                      Double area,
                                                                                                      Pageable pageable);

    @Query(value = "SELECT count(*) FROM trade t WHERE t.updated_date >= ?1", nativeQuery = true)
    Long countByUpdatedDateGreaterThanEqual(Date updatedDate);

    @Query(value = "SELECT count(*) FROM trade t WHERE t.created_date >= ?1", nativeQuery = true)
    Long countByCreatedDateGreaterThanEqual(Date createdDate);

    List<Trade> findByDateBetweenAndDongCodeAndName(String startDate, String endDate, String dongCode, String name);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND sido_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndSidoCode(String startDate, String endDate, String sidoCode);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND gungu_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndGunguCode(String startDate, String endDate, String gunguCode);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND dong_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndDongCode(String startDate, String endDate, String dongCode);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND sido_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndSidoCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String sidoCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND gungu_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndGunguCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String gunguCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM trade WHERE (date BETWEEN ?1 AND ?2) AND dong_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndDongCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String dongCode, Date createdDate);
}
