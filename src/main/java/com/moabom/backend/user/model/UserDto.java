package com.moabom.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String nickName;
    private String userImage;
}

