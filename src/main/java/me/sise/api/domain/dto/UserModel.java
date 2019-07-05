package me.sise.api.domain.dto;

import lombok.Data;

@Data
public class UserModel {
    private String providerType;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String role;
}
