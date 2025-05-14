package com.moabom.backend.model;

import lombok.Data;

@Data
public class SignupRequest {
    private String userId;
    private String password;
    private String nickName;
}

