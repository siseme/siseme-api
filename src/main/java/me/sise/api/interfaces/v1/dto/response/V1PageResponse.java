package me.sise.api.interfaces.v1.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class V1PageResponse<T> {
    private Integer pageNumber;
    private Integer totalPages;
    private Long totalElements;
    private List<T> contents;
    private String shortUrl;
}
