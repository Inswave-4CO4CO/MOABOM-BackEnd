package com.moabom.backend.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilmographyDto {
    private Integer contentId;
    private String title;
    private String category;
    private String roleType; // "cast" 또는 "crew"
    private String role; // CastRole 또는 CrewRole의 문자열 값
    private String poster;
    private Integer releaseYear;
} 