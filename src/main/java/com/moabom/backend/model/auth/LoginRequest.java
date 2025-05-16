package com.moabom.backend.model.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
