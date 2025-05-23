package com.moabom.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyStatsDTO {
    private String genreName;
    private Integer count;
}
