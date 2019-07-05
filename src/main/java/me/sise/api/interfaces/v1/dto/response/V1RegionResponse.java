package me.sise.api.interfaces.v1.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class V1RegionResponse implements Serializable {
    private static final long serialVersionUID = 5611900617803283397L;
    private String type;
    private String code;
    private String name;
    private String fullName;
}
