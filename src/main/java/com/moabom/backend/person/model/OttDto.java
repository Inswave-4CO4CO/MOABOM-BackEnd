package com.moabom.backend.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OttDto {
    private Integer ottId;
    private String ottName;
    private String url;
} 