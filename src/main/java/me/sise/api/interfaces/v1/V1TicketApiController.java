package me.sise.api.interfaces.v1;

import me.sise.api.domain.service.TicketService;
import me.sise.api.interfaces.v1.dto.response.V1MaxTradeResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1StatsResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeCountResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev/api/v1/tickets")
public class V1TicketApiController {
    private final TicketService ticketService;

    public V1TicketApiController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/max/regions/{regionCode}/pages/{pageNo}/size/{size}")
    public V1PageResponse<V1MaxTradeResponse> getMaxTradeListByPaging(@PathVariable("regionCode") String regionCode,
                                                                      @PathVariable("pageNo") String pageNo,
                                                                      @PathVariable("size") String size,
                                                                      @RequestParam(value = "date") String date,
                                                                      @RequestParam(value = "searchType") String searchType,
                                                                      @RequestParam(value = "sortType", required = false, defaultValue = "monthType") String sortType,
                                                                      @RequestParam(value = "areaType", required = false, defaultValue = "0") String areaType,
                                                                      @RequestParam(value = "rangeType", required = false, defaultValue = "1") String rangeType) {
        return ticketService.getMaxTradeListByPaging(date, regionCode, searchType, pageNo, size, sortType, areaType, rangeType);
    }

    @GetMapping("/max/regions/{regionCode}/stats")
    public V1StatsResponse getStats(@PathVariable("regionCode") String regionCode,
                                    @RequestParam(value = "date") String date,
                                    @RequestParam(value = "rangeType", required = false, defaultValue = "1") String rangeType) {
        return ticketService.getStats(regionCode, date, rangeType);
    }

    @GetMapping("/max/regions/{regionCode}/count")
    public List<V1TradeCountResponse> getTradeCount(@PathVariable("regionCode") String regionCode,
                                                    @RequestParam(value = "date") String date,
                                                    @RequestParam(value = "rangeType", required = false, defaultValue = "1") String rangeType) {
        return ticketService.getTradeCount(regionCode, date, rangeType);
    }
}
