package me.sise.api.interfaces.v2;

import me.sise.api.domain.service.RentRankService;
import me.sise.api.domain.service.TicketRankService;
import me.sise.api.domain.service.TradeRankService;
import me.sise.api.interfaces.v2.dto.RentRankResponse;
import me.sise.api.interfaces.v2.dto.TicketRankResponse;
import me.sise.api.interfaces.v2.dto.TradeRankResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/dev/api/v2")
public class RankController {

    private final TradeRankService tradeRankService;
    private final TicketRankService ticketRankService;
    private final RentRankService rentRankService;

    public RankController(TradeRankService tradeRankService,
                          TicketRankService ticketRankService,
                          RentRankService rentRankService) {
        this.tradeRankService = tradeRankService;
        this.ticketRankService = ticketRankService;
        this.rentRankService = rentRankService;
    }

    @GetMapping("/trade/ranks")
    public TradeRankResponse getTradeRanks(@RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate,
                                           @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                           @RequestParam(value = "regionCode") String regionCode) {
        return tradeRankService.getTradeRank(startDate, endDate, searchType, regionCode);
    }

    @GetMapping("/ticket/ranks")
    public TicketRankResponse getTicketRanks(@RequestParam(value = "startDate") String startDate,
                                             @RequestParam(value = "endDate") String endDate,
                                             @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                             @RequestParam(value = "regionCode") String regionCode) {
        return ticketRankService.getTicketRank(startDate, endDate, searchType, regionCode);
    }

    @GetMapping("/rent/ranks")
    public RentRankResponse getRentRanks(@RequestParam(value = "startDate") String startDate,
                                         @RequestParam(value = "endDate") String endDate,
                                         @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                         @RequestParam(value = "regionCode") String regionCode) {
        return rentRankService.getRentRank(startDate, endDate, searchType, regionCode);
    }
}
