package com.moabom.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CastAndCrewDTO {
    private int personId;
    private String personName;
    private String image;
    private String role;
}
