package com.moabom.backend.person.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDetailDto {
    private Integer personId;
    private String personName;
    private String image;
    private List<PersonFilmographyDto> filmography;
} 