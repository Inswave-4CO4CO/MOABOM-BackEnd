package com.moabom.backend.auth.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
