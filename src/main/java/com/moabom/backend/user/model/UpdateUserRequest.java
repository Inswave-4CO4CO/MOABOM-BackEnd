package com.moabom.backend.user.model;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String nickName;
    private String userImage;
}
