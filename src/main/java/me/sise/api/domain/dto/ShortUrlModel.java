package me.sise.api.domain.dto;

import lombok.Data;

@Data
public class ShortUrlModel {
    private Long id;
    private String userId;
    private String path;
    private String webUrl;
    private String createdDate;
}
