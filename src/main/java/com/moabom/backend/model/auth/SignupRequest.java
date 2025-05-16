package com.moabom.backend.model.auth;

import lombok.Data;

@Data
public class SignupRequest {
    private String userId;
    private String password;
    private String nickName;
}

