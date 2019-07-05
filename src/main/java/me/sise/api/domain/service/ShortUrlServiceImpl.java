package me.sise.api.domain.service;

import lombok.extern.slf4j.Slf4j;
import me.sise.api.application.common.util.ShortUrlBuilder;
import me.sise.api.domain.dto.ShortUrlModel;
import me.sise.api.domain.exception.UrlShorteningFailureException;
import me.sise.api.infrastructure.persistence.jpa.entity.AptName;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.entity.ShortUrl;
import me.sise.api.infrastructure.persistence.jpa.entity.SocialUser;
import me.sise.api.infrastructure.persistence.jpa.entity.TradeType;
import me.sise.api.infrastructure.persistence.jpa.repository.AptNameRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.ShortUrlRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.SocialUserRepository;
import me.sise.api.interfaces.support.NoResultFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final static Integer MAX_LENGTH = 4;
    private final static String MAIN_URL = "http://www.naver.com";
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final ShortUrlBuilder shortUrlBuilder;
    private final ShortUrlRepository shortUrlRepository;
    private final SocialUserRepository socialUserRepository;
    private final RegionRepository regionRepository;
    private final AptNameRepository aptNameRepository;

    public ShortUrlServiceImpl(ShortUrlBuilder shortUrlBuilder,
                               ShortUrlRepository shortUrlRepository,
                               SocialUserRepository socialUserRepository,
                               RegionRepository regionRepository,
                               AptNameRepository aptNameRepository) {
        this.shortUrlBuilder = shortUrlBuilder;
        this.shortUrlRepository = shortUrlRepository;
        this.socialUserRepository = socialUserRepository;
        this.regionRepository = regionRepository;
        this.aptNameRepository = aptNameRepository;
    }

    @Override
    public ShortUrlModel createShortUrl(String path,
                                        String webUrl,
                                        String sidoCode,
                                        String gunguCode,
                                        String dongCode,
                                        String date) {
        String newPath = date;
        String regionName = "";
        if (StringUtils.isNotEmpty(dongCode)) {
            regionName = regionRepository.findByCode(dongCode)
                                         .getFullName();
        } else if (StringUtils.isNotEmpty(gunguCode)) {
            regionName = regionRepository.findByCode(gunguCode)
                                         .getFullName();
        } else if (StringUtils.isNotEmpty(sidoCode)) {
            regionName = regionRepository.findByCode(sidoCode)
                                         .getFullName();
        }
        newPath = newPath + "_" + (regionName.replaceAll("\\p{Z}", ""));
        return createShortUrl(newPath, webUrl);
    }

    @Override
    public ShortUrlModel createShortUrl(String path, String webUrl) {
        return this.createShortUrl("dev", path, webUrl);
    }

    @Override
    public ShortUrlModel createShortUrl(String userId, String path, String webUrl) {
        ShortUrl existWebUrl = shortUrlRepository.findByWebUrl(webUrl);
        if (!ObjectUtils.isEmpty(existWebUrl)) {
            return transform(existWebUrl);
        }
        existWebUrl = shortUrlRepository.findByPath(path);
        if (!ObjectUtils.isEmpty(existWebUrl)) {
            return transform(existWebUrl);
        }
        String shortPath = StringUtils.isEmpty(path) ? shortUrlBuilder.shorten(MAX_LENGTH) : path;
        if (existsPath(shortPath)) {
            if (StringUtils.isEmpty(path)) {
                // Todo 랜덤으로 생성한 Path가 중복일 경우에는 따로 처리가 필요함
                throw new UrlShorteningFailureException.PathIsAlreadyInUseException();
            }
            throw new UrlShorteningFailureException.CustomPathIsAlreadyInUseException();
        }
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setUser(socialUserRepository.findByProviderUserId(userId));
        shortUrl.setPath(shortPath);
        shortUrl.setWebUrl(webUrl);
        shortUrl.setIsActive(Boolean.TRUE);
        return transform(shortUrlRepository.save(shortUrl));
    }

    @Override
    public ShortUrlModel updateShortUrl(String userId, String path, String customPath, String webUrl) {
        if (!existsPath(path)) {
            throw new NoResultFoundException();
        }
        if (!checkUserId(userId, path)) {
            throw new NoResultFoundException();
        }
        ShortUrl shortUrlResult = shortUrlRepository.findByPath(path);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(shortUrlResult.getId());
        shortUrl.setPath(StringUtils.isEmpty(customPath) ? shortUrlResult.getPath() : customPath);
        shortUrl.setWebUrl(StringUtils.isEmpty(webUrl) ? shortUrlResult.getWebUrl() : webUrl);
        shortUrl.setIsActive(shortUrlResult.getIsActive());
        return transform(shortUrlRepository.save(shortUrl));
    }

    @Override
    public ShortUrlModel readShortUrl(String path) {
        if (!existsPath(path)) {
            String[] pathArray = path.split("_");
            if (pathArray.length == 3) {
                String regionName = pathArray[0];
                String name = pathArray[1];
                String tradeType = pathArray[2].split("\\(")[0];
                if ("실거래가".equals(tradeType)) {
                    tradeType = TradeType.TRADE.getName();
                } else if ("분양권".equals(tradeType)) {
                    tradeType = TradeType.TICKET.getName();
                } else if ("전월세".equals(tradeType)) {
                    tradeType = TradeType.RENT.getName();
                } else {
                    throw new NoResultFoundException();
                }
                String dateArray = pathArray[2].split("\\(")[1].replace(")", "");
                YearMonth startYearMonth = YearMonth.parse(dateArray.split("~")[0], DateTimeFormatter.ofPattern("yyyy년MM월"));
                YearMonth endYearMonth = YearMonth.parse(dateArray.split("~")[1], DateTimeFormatter.ofPattern("yyyy년MM월"));
                if (startYearMonth.isAfter(endYearMonth) || endYearMonth.isAfter(YearMonth.now())) {
                    throw new NoResultFoundException();
                }
                List<AptName> byName = aptNameRepository.findByName(name);
                List<Region> regionList = byName.stream()
                                                .map(x -> regionRepository.findByCode(x.getDongCode()))
                                                .collect(Collectors.toList());
                Optional<Region> regionOptional = regionList.stream()
                                                            .filter(x -> x.getFullName()
                                                                          .replaceAll("\\p{Z}","")
                                                                          .equals(regionName))
                                                            .findFirst();
                if (regionOptional.isPresent()) {
                    Region region = regionOptional.get();
                    Optional<AptName> aptNameOptional = byName.stream()
                                                              .filter(x -> x.getDongCode()
                                                                            .equals(region.getCode()))
                                                              .findFirst();
                    if (aptNameOptional.isPresent()) {
                        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                        AptName aptName = aptNameOptional.get();
                        String startDate = startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
                        String endDate = endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
                        params.add("code", aptName.getDongCode());
                        params.add("name", aptName.getName());
                        params.add("startDate", startDate);
                        params.add("endDate", endDate);
                        params.add("type", "apt");
                        params.add("tradeType", tradeType);
                        String url = UriComponentsBuilder.newInstance()
                                                         .scheme("https")
                                                         .host("sise.me")
                                                         .queryParams(params)
                                                         .build()
                                                         .toUriString();
                        return createShortUrlBySiseme(url, aptName.getDongCode(), startDate, endDate, "apt", aptName.getName(), tradeType);
                    } else {
                        throw new NoResultFoundException();
                    }
                } else {
                    throw new NoResultFoundException();
                }
            } else if (pathArray.length == 2) {
                String regionName = pathArray[0];
                String tradeType = pathArray[1].split("\\(")[0];
                if ("실거래가".equals(tradeType)) {
                    tradeType = TradeType.TRADE.getName();
                } else if ("분양권".equals(tradeType)) {
                    tradeType = TradeType.TICKET.getName();
                } else if ("전월세".equals(tradeType)) {
                    tradeType = TradeType.RENT.getName();
                } else {
                    throw new NoResultFoundException();
                }
                String dateArray = pathArray[1].split("\\(")[1].replace(")", "");
                YearMonth startYearMonth = YearMonth.parse(dateArray.split("~")[0], DateTimeFormatter.ofPattern("yyyy년MM월"));
                YearMonth endYearMonth = YearMonth.parse(dateArray.split("~")[1], DateTimeFormatter.ofPattern("yyyy년MM월"));
                if (startYearMonth.isAfter(endYearMonth) || endYearMonth.isAfter(YearMonth.now())) {
                    throw new NoResultFoundException();
                }
                List<Region> sidoList = regionRepository.findByType(RegionType.SIDO);
                Optional<Region> regionOptional = sidoList.stream()
                                                          .filter(x -> x.getFullName().replaceAll("\\p{Z}","").equals(regionName))
                                                          .findFirst();
                if (!regionOptional.isPresent()) {
                    List<Region> gunguList = regionRepository.findByType(RegionType.GUNGU);
                    regionOptional = gunguList.stream()
                                              .filter(x -> x.getFullName().replaceAll("\\p{Z}","").equals(regionName))
                                              .findFirst();
                    if (!regionOptional.isPresent()) {
                        List<Region> dongList = regionRepository.findByType(RegionType.DONG);
                        regionOptional = dongList.stream()
                                                 .filter(x -> x.getFullName().replaceAll("\\p{Z}","").equals(regionName))
                                                 .findFirst();
                        if (!regionOptional.isPresent()) {
                            throw new NoResultFoundException();
                        }
                    }
                }
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                Region region = regionOptional.get();
                String startDate = startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
                String endDate = endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
                params.add("code", region.getCode());
                params.add("name", region.getFullName().replaceAll("\\p{Z}",""));
                params.add("startDate", startDate);
                params.add("endDate", endDate);
                params.add("type", region.getType().name().toLowerCase());
                params.add("tradeType", tradeType);
                String url = UriComponentsBuilder.newInstance()
                                                 .scheme("https")
                                                 .host("sise.me")
                                                 .queryParams(params)
                                                 .build()
                                                 .toUriString();
                return createShortUrlBySiseme(url, region.getCode(), startDate, endDate, region.getType().name().toLowerCase(), region.getFullName(), tradeType);
            } else {
                throw new NoResultFoundException();
            }
        }
        return transform(shortUrlRepository.findByPath(path));
    }

    @Override
    public ShortUrlModel deleteShortUrl(String userId, String path) {
        if (!existsPath(path)) {
            throw new NoResultFoundException();
        }
        ShortUrl shortUrlResult = shortUrlRepository.findByPath(path);
        if (!checkUserId(userId, path)) {
            throw new NoResultFoundException();
        }
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(shortUrlResult.getId());
        shortUrl.setPath(shortUrlResult.getPath());
        shortUrl.setWebUrl(shortUrlResult.getWebUrl());
        shortUrl.setIsActive(Boolean.FALSE);
        return transform(shortUrlRepository.save(shortUrl));
    }

    private Boolean checkUserId(String userId, String path) {
        return shortUrlRepository.findByPath(path)
                                 .getUser()
                                 .getProviderUserId()
                                 .equals(userId);
    }

    @Override
    public String getRouteUrl(String path) {
        ShortUrl shortUrl = shortUrlRepository.findByPath(path);
        return Objects.isNull(shortUrl) ? MAIN_URL : shortUrl.getWebUrl();
    }

    @Override
    public List<ShortUrlModel> readShortUrls(String userId) {
        SocialUser user = socialUserRepository.findByProviderUserId(userId);
        return shortUrlRepository.findByUser(user)
                                 .stream()
                                 .map(this::transform)
                                 .collect(Collectors.toList());
    }

    private String getTradeName(String searchType) {
        if ("trade".equals(searchType)) {
            return "실거래가";
        } else if ("ticket".equals(searchType)) {
            return "분양권";
        } else if ("rent".equals(searchType)) {
            return "전월세";
        } else {
            return "실거래가";
        }
    }

    @Override
    public ShortUrlModel createShortUrlBySiseme(String webUrl,
                                                String regionCode,
                                                String startDate,
                                                String endDate,
                                                String regionType,
                                                String regionName,
                                                String tradeType) {
        String newPath = "(" + startDate + "~" + endDate + ")";
        if ("apt".equals(regionType)) {
            try {
                AptName aptName = aptNameRepository.findByDongCodeAndName(regionCode, regionName);
                Region region = regionRepository.findByCode(regionCode);
                newPath = (region.getFullName()
                                 .replaceAll("\\p{Z}", "")) + "_" + (aptName.getName()
                                                                            .replaceAll("\\p{Z}",
                                                                                        "")) + "_" + this.getTradeName(tradeType) + newPath;
            } catch (NullPointerException e) {
                log.debug("# createShortUrlBySiseme null.. {} / {} / {}", webUrl, regionCode, regionName);
            }
        } else {
            try {
                Region region = regionRepository.findByCode(regionCode);
                newPath = (region.getFullName()
                                 .replaceAll("\\p{Z}", "")) + "_" + this.getTradeName(tradeType) + newPath;
            } catch (NullPointerException e) {
                log.debug("# createShortUrlBySiseme null.. {} / {} / {}", webUrl, regionCode, regionName);
            }
        }
        return createShortUrl(newPath, webUrl);
    }

    @Override
    public ShortUrlModel getShortUrl(String id) {
        return this.transform(shortUrlRepository.findById(Long.valueOf(id)).orElseThrow(IllegalArgumentException::new));
    }

    private Boolean existsPath(String path) {
        ShortUrl shortUrl = shortUrlRepository.findByPath(path);
        return Objects.isNull(shortUrl) ? Boolean.FALSE : shortUrl.getIsActive();
    }

    private ShortUrlModel transform(ShortUrl shortUrl) {
        ShortUrlModel shortUrlModel = new ShortUrlModel();
        shortUrlModel.setId(shortUrl.getId());
        shortUrlModel.setUserId("dev");
        shortUrlModel.setPath(shortUrl.getPath());
        shortUrlModel.setWebUrl(shortUrl.getWebUrl());
        shortUrlModel.setCreatedDate(shortUrl.getCreatedDateTime()
                                             .format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        return shortUrlModel;
    }
}
