package me.sise.api.interfaces.v2;

import me.sise.api.domain.service.RegionCodeService;
import me.sise.api.domain.service.ShortUrlService;
import me.sise.api.infrastructure.persistence.jpa.entity.MainStats;
import me.sise.api.infrastructure.persistence.jpa.repository.MainStatsRepository;
import me.sise.api.interfaces.v1.dto.response.V1RegionResponse;
import me.sise.api.interfaces.v1.dto.response.V1ShortUrlResponse;
import me.sise.api.interfaces.v2.dto.ShortUrlCreateRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/dev/api/v2/search")
public class SearchController {
    private final RegionCodeService regionCodeService;
    private final ShortUrlService shortUrlService;
    private final MainStatsRepository mainStatsRepository;

    public SearchController(RegionCodeService regionCodeService,
                            ShortUrlService shortUrlService,
                            MainStatsRepository mainStatsRepository) {
        this.regionCodeService = regionCodeService;
        this.shortUrlService = shortUrlService;
        this.mainStatsRepository = mainStatsRepository;
    }

    // fixme 임시
    @GetMapping("/regions/{regionCode}/aptname")
    public List<V1RegionResponse> getAptName(@PathVariable("regionCode") String regionCode, @RequestParam(value = "tradeType", required = false, defaultValue = "trade") String tradeType) {
        return regionCodeService.getApts(regionCode, tradeType);
    }

    // fixme 임시
    @GetMapping("/regions/{regionCode}/aptname/{aptName}")
    public V1RegionResponse getApt(@PathVariable("regionCode") String regionCode, @PathVariable("aptName") String aptName, @RequestParam(value = "tradeType", required = false, defaultValue = "trade") String tradeType) {
        return regionCodeService.getApt(regionCode, aptName, tradeType);
    }

    @GetMapping("/regions")
    public List<V1RegionResponse> getRegions(@RequestParam("keyword") String keyword) {
        return regionCodeService.getRegions(keyword);
    }

    @GetMapping("/regions/{regionCode}")
    public V1RegionResponse getRegion(@PathVariable("regionCode") String regionCode) {
        return regionCodeService.getRegion(regionCode);
    }

    @GetMapping("/regions/type/{regionType}")
    public List<V1RegionResponse> getRegionListByType(@PathVariable("regionType") String regionType) {
        return regionCodeService.getResionListByType(regionType);
    }

    @GetMapping("/regions/like/{regionCode}")
    public List<V1RegionResponse> getResionListByCodeLike(@PathVariable("regionCode") String regionCode) {
        return regionCodeService.getResionListByCodeLike(regionCode);
    }

    @GetMapping("/regions/type/{regionType}/like/{regionCode}")
    public List<V1RegionResponse> getRegionListByTypeAndCodeLike(@PathVariable("regionType") String regionType,
                                                                 @PathVariable("regionCode") String regionCode,
                                                                 @RequestParam(required = false, defaultValue = "") String ts) {
        return regionCodeService.getRegionListByTypeAndCodeLike(regionType, regionCode);
    }

    @GetMapping("/regions/name/{regionName}")
    public V1RegionResponse getRegionByFullName(@PathVariable("regionName") String regionName) {
        return regionCodeService.getRegionByFullName(regionName);
    }

    @PostMapping("/shorten")
    public V1ShortUrlResponse createShortUrl(@RequestBody ShortUrlCreateRequest request) {
        return new V1ShortUrlResponse(shortUrlService.createShortUrlBySiseme(request.getWebUrl(),
                                                                             request.getRegionCode(),
                                                                             request.getStartDate(),
                                                                             request.getEndDate(),
                                                                             request.getRegionType(),
                                                                             request.getRegionName(),
                                                                             request.getTradeType()));
    }

    @GetMapping("/shorten/{id}")
    public V1ShortUrlResponse getShortUrl(@PathVariable String id) {
        return new V1ShortUrlResponse(shortUrlService.getShortUrl(id));
    }

    @GetMapping("/stats")
    public MainStats getMainStats(@RequestParam("date") String date, @RequestParam(required = false, defaultValue = "-1") String regionCode) {
        return mainStatsRepository.findByDateAndRegionCode(date, regionCode);
    }
}
