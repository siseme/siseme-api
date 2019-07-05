package me.sise.api.interfaces.v2;

import lombok.RequiredArgsConstructor;
import me.sise.api.domain.dto.BubbleIndexModel;
import me.sise.api.domain.service.RegionStatsService;
import me.sise.api.interfaces.v2.dto.BubbleIndexResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/api/v2/region/")
@RequiredArgsConstructor
public class RegionStatsController {

    private final RegionStatsService regionStatsService;

    @GetMapping("bubble")
    public BubbleIndexResponse getBubbleIndex(@RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate,
                                           @RequestParam(value = "regionCode") String regionCode) {
        BubbleIndexModel bubbleIndexModel = regionStatsService.getBubbleIndex(regionCode, startDate, endDate);
        return BubbleIndexResponse.of(bubbleIndexModel);
    }
}
