package com.moabom.backend.user.model;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String newPassword;
}
