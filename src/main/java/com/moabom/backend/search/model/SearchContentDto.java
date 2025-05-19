// SearchContentDto.java
package com.moabom.backend.search.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchContentDto {
    private Long contentId;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String category;
    private String runningTime;
    private String image;
    private Float rating;
    private Integer ageRating;
    private String madeIn;
    private String poster;
    private Float imdbRating;
    private List<String> genres;
    private List<String> otts;
}