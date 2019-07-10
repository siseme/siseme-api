package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByDateAndSidoCode(String date, String sidoCode);

    Page<Ticket> findByDateAndSidoCode(String date, String regionCode, Pageable pageable);

    Page<Ticket> findByDateAndSidoCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);

    Page<Ticket> findByDateAndSidoCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Ticket> findByDateAndGunguCode(String date, String gunguCode);

    Page<Ticket> findByDateAndGunguCode(String date, String regionCode, Pageable pageable);

    Page<Ticket> findByDateAndGunguCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);

    Page<Ticket> findByDateAndGunguCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Ticket> findByDateAndDongCode(String date, String dongCode);

    Page<Ticket> findByDateAndDongCode(String date, String regionCode, Pageable pageable);

    Page<Ticket> findByDateAndDongCodeAndAreaBetween(String date, String regionCode, Double startArea, Double endArea, Pageable pageable);

    Page<Ticket> findByDateAndDongCodeAndAreaGreaterThanEqual(String date, String regionCode, Double area, Pageable pageable);

    List<Ticket> findByDate(String date);

    @Query(value = "SELECT * FROM ticket t WHERE t.date BETWEEN ?1 and ?2 and t.dong_code = ?3 and t.name = ?4 and t.area = ?5", nativeQuery = true)
    List<Ticket> findByDateBetweenAndDongCodeAndNameAndAreaOrderByMainPrice(String startDate,
                                                                            String endDate,
                                                                            String dongCode,
                                                                            String name,
                                                                            Double area);
    List<Ticket> findByDateLessThanEqualAndDongCodeAndNameAndAreaOrderByMainPrice(String date, String dongCode, String name, Double area);

    @Query(value = "SELECT IFNULL(MAX(t.main_price), 0) from ticket t " +
        "where t.date <= :date and t.dong_code = :dongCode and t.name = :name " +
        "and t.area = :area and t.open_api_ticket_info_id != :openApiTicketInfoId", nativeQuery = true)
    Integer findMaxPrice(@Param("date") String date,
                         @Param("dongCode") String dongCode,
                         @Param("name") String name,
                         @Param("area") Double area,
                         @Param("openApiTicketInfoId") Long openApiTicketInfoId);

    Page<Ticket> findByDateBetweenAndDongCodeAndName(String startDate, String endDate, String dongCode, String name, Pageable pageable);
    Page<Ticket> findByDateBetweenAndDongCodeAndNameAndAreaBetween(String startDate,
                                                                   String endDate,
                                                                   String dongCode,
                                                                   String name,
                                                                   Double startArea,
                                                                   Double endArea,
                                                                   Pageable pageable);
    Page<Ticket> findByDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(String startDate,
                                                                            String endDate,
                                                                            String dongCode,
                                                                            String name,
                                                                            Double area,
                                                                            Pageable pageable);

    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndName(Date updatedDate,
                                                                                       String startDate,
                                                                                       String endDate,
                                                                                       String dongCode,
                                                                                       String name,
                                                                                       Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaBetween(Date updatedDate,
                                                                                                     String startDate,
                                                                                                     String endDate,
                                                                                                     String dongCode,
                                                                                                     String name,
                                                                                                     Double startArea,
                                                                                                     Double endArea,
                                                                                                     Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndNameAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                              String startDate,
                                                                                                              String endDate,
                                                                                                              String dongCode,
                                                                                                              String name,
                                                                                                              Double area,
                                                                                                              Pageable pageable);

    Page<Ticket> findByDateBetweenAndSidoCode(String startDate, String endDate, String sidoCode, Pageable pageable);
    Page<Ticket> findByDateBetweenAndSidoCodeAndAreaBetween(String startDate,
                                                            String endDate,
                                                            String sidoCode,
                                                            Double startArea,
                                                            Double endArea,
                                                            Pageable pageable);
    Page<Ticket> findByDateBetweenAndSidoCodeAndAreaGreaterThanEqual(String startDate,
                                                                     String endDate,
                                                                     String sidoCode,
                                                                     Double area,
                                                                     Pageable pageable);

    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCode(Date updatedDate,
                                                                                String startDate,
                                                                                String endDate,
                                                                                String sidoCode,
                                                                                Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaBetween(Date updatedDate,
                                                                                              String startDate,
                                                                                              String endDate,
                                                                                              String sidoCode,
                                                                                              Double startArea,
                                                                                              Double endArea,
                                                                                              Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndSidoCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                       String startDate,
                                                                                                       String endDate,
                                                                                                       String sidoCode,
                                                                                                       Double area,
                                                                                                       Pageable pageable);

    Page<Ticket> findByDateBetweenAndGunguCode(String startDate, String endDate, String gunguCode, Pageable pageable);
    Page<Ticket> findByDateBetweenAndGunguCodeAndAreaBetween(String startDate,
                                                             String endDate,
                                                             String gunguCode,
                                                             Double startArea,
                                                             Double endArea,
                                                             Pageable pageable);
    Page<Ticket> findByDateBetweenAndGunguCodeAndAreaGreaterThanEqual(String startDate,
                                                                      String endDate,
                                                                      String gunguCode,
                                                                      Double area,
                                                                      Pageable pageable);

    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCode(Date updatedDate,
                                                                                 String startDate,
                                                                                 String endDate,
                                                                                 String gunguCode,
                                                                                 Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaBetween(Date updatedDate,
                                                                                               String startDate,
                                                                                               String endDate,
                                                                                               String gunguCode,
                                                                                               Double startArea,
                                                                                               Double endArea,
                                                                                               Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndGunguCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                        String startDate,
                                                                                                        String endDate,
                                                                                                        String gunguCode,
                                                                                                        Double area,
                                                                                                        Pageable pageable);

    Page<Ticket> findByDateBetweenAndDongCode(String startDate, String endDate, String dongCode, Pageable pageable);
    Page<Ticket> findByDateBetweenAndDongCodeAndAreaBetween(String startDate,
                                                            String endDate,
                                                            String dongCode,
                                                            Double startArea,
                                                            Double endArea,
                                                            Pageable pageable);
    Page<Ticket> findByDateBetweenAndDongCodeAndAreaGreaterThanEqual(String startDate,
                                                                     String endDate,
                                                                     String dongCode,
                                                                     Double area,
                                                                     Pageable pageable);

    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCode(Date updatedDate,
                                                                                String startDate,
                                                                                String endDate,
                                                                                String dongCode,
                                                                                Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaBetween(Date updatedDate,
                                                                                              String startDate,
                                                                                              String endDate,
                                                                                              String dongCode,
                                                                                              Double startArea,
                                                                                              Double endArea,
                                                                                              Pageable pageable);
    Page<Ticket> findByCreatedDateTimeGreaterThanEqualAndDateBetweenAndDongCodeAndAreaGreaterThanEqual(Date updatedDate,
                                                                                                       String startDate,
                                                                                                       String endDate,
                                                                                                       String dongCode,
                                                                                                       Double area,
                                                                                                       Pageable pageable);

    @Query(value = "SELECT count(*) FROM ticket t WHERE t.created_date >= ?1", nativeQuery = true)
    Long countByCreatedDateGreaterThanEqual(Date parse);

    List<Ticket> findByDateBetweenAndDongCodeAndName(String startDate, String endDate, String dongCode, String name);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND sido_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndSidoCode(String startDate, String endDate, String sidoCode);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND gungu_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndGunguCode(String startDate, String endDate, String gunguCode);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND dong_code = ?3 AND main_price > max_price", nativeQuery = true)
    Long countByDateBetweenAndDongCode(String startDate, String endDate, String dongCode);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND sido_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndSidoCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String sidoCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND gungu_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndGunguCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String gunguCode, Date createdDate);

    @Query(value = "SELECT count(*) FROM ticket WHERE (date BETWEEN ?1 AND ?2) AND dong_code = ?3 AND created_date >= ?4", nativeQuery = true)
    Long countByDateBetweenAndDongCodeAndCreatedDateTimeGreaterThanEqual(String startDate, String endDate, String dongCode, Date createdDate);
}
