package me.sise.api.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import me.sise.api.domain.dto.RegionCodeModel;
import me.sise.api.infrastructure.persistence.jpa.entity.Apartment;
import me.sise.api.infrastructure.persistence.jpa.entity.Region;
import me.sise.api.infrastructure.persistence.jpa.entity.RegionType;
import me.sise.api.infrastructure.persistence.jpa.repository.ApartmentRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.AptNameRepository;
import me.sise.api.infrastructure.persistence.jpa.repository.RegionRepository;
import me.sise.api.interfaces.v1.dto.response.V1RegionResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RegionCodeServiceImpl implements RegionCodeService {
    private final RegionRepository regionRepository;
    private final AptNameRepository aptNameRepository;
    private final ApartmentRepository apartmentRepository;

    public RegionCodeServiceImpl(RegionRepository regionRepository,
                                 AptNameRepository aptNameRepository, ApartmentRepository apartmentRepository) {
        this.regionRepository = regionRepository;
        this.aptNameRepository = aptNameRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Cacheable(value = "getRegion")
    @Override
    public V1RegionResponse getRegion(String code) {
        return transform(regionRepository.findByCode(code));
    }

    @Cacheable(value = "getResionListByCodeLike")
    @Override
    public List<V1RegionResponse> getResionListByCodeLike(String code) {
        return regionRepository.findByCodeLike(code + "%")
                               .stream()
                               .map(this::transform)
                               .collect(Collectors.toList());
    }

    @Cacheable(value = "getResionListByType")
    @Override
    public List<V1RegionResponse> getResionListByType(String type) {
        try {
            return regionRepository.findByTypeOrderByNameAsc(transform(type))
                                   .stream()
                                   .map(this::transform)
                                   .collect(Collectors.toList());
        } catch (IllegalArgumentException iae) {
            return Lists.newArrayList();
        }
    }

    //    @PostConstruct
    @Override
    public void readRegionCodeJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new ClassPathResource("data/regionCode.json").getFile();
            List<RegionCodeModel> regionCodeModelList = objectMapper.readValue(file, new TypeReference<List<RegionCodeModel>>() {
            });
            List<Region> regionList = regionCodeModelList.stream()
                                                         .filter(distinctByKey(RegionCodeModel::getAdmCode))
                                                         .filter(distinctByKey(RegionCodeModel::getLowestAdmCodeNm))
                                                         .map(regionCodeModel -> {
                                                             Region region = new Region();
                                                             RegionType type = RegionType.UNKNOWN;
                                                             if (regionCodeModel.getType()
                                                                                .equals(RegionType.SIDO.name()
                                                                                                       .toLowerCase())) {
                                                                 type = RegionType.SIDO;
                                                             } else if (regionCodeModel.getType()
                                                                                       .equals(RegionType.GUNGU.name()
                                                                                                               .toLowerCase())) {
                                                                 type = RegionType.GUNGU;
                                                             } else if (regionCodeModel.getType()
                                                                                       .equals(RegionType.DONG.name()
                                                                                                              .toLowerCase())) {
                                                                 type = RegionType.DONG;
                                                             }
                                                             region.setType(type);
                                                             region.setCode(regionCodeModel.getAdmCode());
                                                             region.setName(regionCodeModel.getAdmCodeNm());
                                                             region.setFullName(regionCodeModel.getLowestAdmCodeNm());
                                                             return region;
                                                         })
                                                         .collect(Collectors.toList());
            regionRepository.saveAll(regionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Cacheable("getRegionListByTypeAndCodeLike")
    @Override
    public List<V1RegionResponse> getRegionListByTypeAndCodeLike(String type, String code) {
        return regionRepository.findByCodeLikeAndTypeOrderByNameAsc(code + "%", transform(type))
                               .stream()
                               .map(this::transform)
                               .collect(Collectors.toList());
    }

    @Cacheable("getRegions")
    @Override
    public List<V1RegionResponse> getRegions(String keyword) {
        Page<Region> pages = regionRepository.findByFullNameContaining(keyword, new PageRequest(0, 30));
        return pages.getContent()
                    .stream()
                    .map(this::transform)
                    .collect(Collectors.toList());
    }

    @Cacheable("getApts")
    @Override
    public List<V1RegionResponse> getApts(String code, String tradeType) {
        return apartmentRepository.findByDongCode(code)
                .stream()
                .filter(distinctByKey(Apartment::getName))
                .map(x -> {
                    Region region = regionRepository.findByCode(x.getDongCode());
                    V1RegionResponse v1RegionResponse = new V1RegionResponse();
                    v1RegionResponse.setType("apt");
                    v1RegionResponse.setCode(x.getDongCode());
                    v1RegionResponse.setFullName(region.getFullName());
                    v1RegionResponse.setName(x.getName());
                    return v1RegionResponse;
                })
                .sorted(Comparator.comparing(V1RegionResponse::getName))
                .collect(Collectors.toList());
    }

    @Cacheable("getApt")
    @Override
    public V1RegionResponse getApt(String dongCode, String name, String tradeType) {
        Apartment byName = apartmentRepository.findByDongCodeAndName(dongCode, name).stream().findFirst().get();
        Region region = regionRepository.findByCode(dongCode);
        V1RegionResponse v1RegionResponse = new V1RegionResponse();
        v1RegionResponse.setType("apt");
        v1RegionResponse.setCode(byName.getDongCode());
        v1RegionResponse.setFullName(region.getFullName());
        v1RegionResponse.setName(byName.getName());
        return v1RegionResponse;
    }

    @Override
    public V1RegionResponse getRegionByFullName(String regionName) {
        List<Region> regionList = regionRepository.findByType(RegionType.SIDO);
        Optional<Region> sido = regionList.stream()
                                          .filter(x -> this.compareString(regionName, x.getFullName()))
                                          .findFirst();
        Region result = new Region();
        if (sido.isPresent()) {
            regionList = regionRepository.findByTypeAndFullNameContaining(RegionType.GUNGU,
                                                                          sido.get()
                                                                              .getFullName());
            Optional<Region> gungu = regionList.stream()
                                               .filter(x -> this.compareString(regionName, x.getFullName()))
                                               .findFirst();
            if (gungu.isPresent()) {
                regionList = regionRepository.findByTypeAndFullNameContaining(RegionType.DONG,
                                                                              sido.get()
                                                                                  .getFullName());
                Optional<Region> dong = regionList.stream()
                                                  .filter(x -> this.compareString(regionName, x.getFullName()))
                                                  .findFirst();
                result = dong.orElseGet(gungu::get);
            } else {
                result = sido.get();
            }
        }
        return this.transform(result);
    }

    private Boolean compareString(String left, String right) {
        return left.replace(" ", "").contains(right.replace(" ", ""));
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private V1RegionResponse transform(Region region) {
        V1RegionResponse v1RegionResponse = new V1RegionResponse();
        v1RegionResponse.setCode(region.getCode());
        v1RegionResponse.setType(region.getType()
                                       .name());
        v1RegionResponse.setName(region.getName());
        v1RegionResponse.setFullName(region.getFullName());
        return v1RegionResponse;
    }

    private RegionType transform(String type) {
        return RegionType.valueOf(type.toUpperCase());
    }
}
