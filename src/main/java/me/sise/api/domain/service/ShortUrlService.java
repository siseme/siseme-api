package me.sise.api.domain.service;

import me.sise.api.domain.dto.ShortUrlModel;

import java.util.List;

public interface ShortUrlService {
    ShortUrlModel createShortUrl(String path, String webUrl, String sidoCode, String gunguCode, String dongCode, String date);

    ShortUrlModel createShortUrl(String path, String webUrl);

    ShortUrlModel createShortUrl(String userId, String path, String webUrl);

    ShortUrlModel updateShortUrl(String userId, String originalPath, String customPath, String webUrl);

    ShortUrlModel readShortUrl(String path);

    ShortUrlModel deleteShortUrl(String userId, String path);

    String getRouteUrl(String path);

    List<ShortUrlModel> readShortUrls(String userId);

    ShortUrlModel createShortUrlBySiseme(String webUrl,
                                         String regionCode,
                                         String startDate,
                                         String endDate,
                                         String regionType,
                                         String regionName,
                                         String tradeType);

    ShortUrlModel getShortUrl(String id);
}
