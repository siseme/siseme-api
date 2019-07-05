package me.sise.api.interfaces.v1;

import me.sise.api.domain.service.RegionCodeService;
import me.sise.api.interfaces.v1.dto.response.V1RegionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev/api/v1/regions")
public class V1RegionApiController {
    private final RegionCodeService regionCodeService;

    public V1RegionApiController(RegionCodeService regionCodeService) {
        this.regionCodeService = regionCodeService;
    }

    @GetMapping("/type/{regionType}")
    public List<V1RegionResponse> getRegionListByType(@PathVariable("regionType") String regionType) {
        return regionCodeService.getResionListByType(regionType);
    }

    @GetMapping("/like/{regionCode}")
    public List<V1RegionResponse> getResionListByCodeLike(@PathVariable("regionCode") String regionCode) {
        return regionCodeService.getResionListByCodeLike(regionCode);
    }

    @GetMapping("/type/{regionType}/like/{regionCode}")
    public List<V1RegionResponse> getRegionListByTypeAndCodeLike(@PathVariable("regionType") String regionType,
                                                                 @PathVariable("regionCode") String regionCode,
                                                                 @RequestParam(required = false, defaultValue = "") String ts) {
        return regionCodeService.getRegionListByTypeAndCodeLike(regionType, regionCode);
    }
}
