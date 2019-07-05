package me.sise.api.interfaces.v1.dto.request;

import lombok.Data;

@Data
public class V1ShortUrlCreateRequest {
    private String path;
    private String webUrl;
    private String sidoCode;
    private String gunguCode;
    private String dongCode;
    private String date;
}
