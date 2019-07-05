package me.sise.api.interfaces;

import me.sise.api.domain.dto.ShortUrlModel;
import me.sise.api.domain.service.RouteLogService;
import me.sise.api.domain.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoutingController {
    private final static String REDIRECT_PREFIX = "redirect:";
    private final ShortUrlService shortUrlService;
    private final RouteLogService routeLogService;

    public RoutingController(ShortUrlService shortUrlService, RouteLogService routeLogService) {
        this.shortUrlService = shortUrlService;
        this.routeLogService = routeLogService;
    }

    @GetMapping("/r/{path}")
    public String route(@PathVariable String path, HttpServletRequest request) {
        logging(path, request);
        ShortUrlModel shortUrlModel = shortUrlService.readShortUrl(path);
        return StringUtils.join(REDIRECT_PREFIX, "https://sise.me?direct=" + shortUrlModel.getId());
    }

    private void logging(String path, HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String referer = request.getHeader(HttpHeaders.REFERER);
        String remoteAddr = request.getRemoteAddr();
        routeLogService.logging(path, userAgent, referer, remoteAddr);
    }
}
