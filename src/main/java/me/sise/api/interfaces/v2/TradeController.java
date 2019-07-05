package me.sise.api.interfaces.v2;

import me.sise.api.domain.service.TradeRankService;
import me.sise.api.domain.service.TradeService;
import me.sise.api.interfaces.v1.dto.response.V1DetailResponse;
import me.sise.api.interfaces.v1.dto.response.V1PageResponse;
import me.sise.api.interfaces.v1.dto.response.V1TradeResponse;
import me.sise.api.interfaces.v2.dto.StatsResponse;
import me.sise.api.interfaces.v2.dto.SummaryResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/dev/api/v2/trade")
public class TradeController {
    private final TradeService tradeService;
    private final TradeRankService tradeRankService;

    public TradeController(TradeService tradeService, TradeRankService tradeRankService) {
        this.tradeService = tradeService;
        this.tradeRankService = tradeRankService;
    }

    @GetMapping
    public V1PageResponse<V1TradeResponse> getTradeList(@RequestParam(value = "startDate") String startDate,
                                                        @RequestParam(value = "endDate") String endDate,
                                                        @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                                        @RequestParam(value = "regionName") String regionName,
                                                        @RequestParam(value = "regionCode") String regionCode,
                                                        @RequestParam(value = "sortType", required = false, defaultValue = "monthType") String sortType,
                                                        @RequestParam(value = "orderType", required = false, defaultValue = "asc") String orderType,
                                                        @RequestParam(value = "areaType", required = false, defaultValue = "0") String areaType,
                                                        @RequestParam(value = "isNewData", required = false, defaultValue = "false") String isNewData,
                                                        @RequestParam(value = "tradeType", required = false, defaultValue = "trade") String tradeType,
                                                        @RequestParam(value = "pageNo") String pageNo,
                                                        @RequestParam(value = "size") String size) {
        return tradeService.getTradeList(startDate, endDate, searchType, regionName, regionCode, sortType, orderType, areaType, isNewData, tradeType, pageNo, size);
    }

    @GetMapping("/detail")
    public V1DetailResponse getDetail(@RequestParam(value = "regionCode") String regionCode, @RequestParam(value = "name") String name, @RequestParam(value = "range", defaultValue = "1") String range) {
        return tradeService.getDetail(regionCode, name, range);
    }

    @GetMapping("/stats")
    public StatsResponse getStats(@RequestParam(value = "tradeType") String tradeType,
                                  @RequestParam(value = "regionCode") String regionCode,
                                  @RequestParam(value = "date") String date,
                                  @RequestParam(value = "rangeType", required = false, defaultValue = "1") String rangeType) {
        return tradeService.getStatsMap(tradeType, regionCode, date, rangeType);
    }

    @GetMapping("/summary")
    public List<SummaryResponse> getSummary(@RequestParam(value = "startDate") String startDate,
                                            @RequestParam(value = "endDate") String endDate,
                                            @RequestParam(value = "regionCode") String regionCode) {
        return tradeService.getSummary(startDate, endDate, regionCode);
    }
}
