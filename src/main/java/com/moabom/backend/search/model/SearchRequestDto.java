package com.moabom.backend.search.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String keyword;
    private String type = "content";
    private List<String> genres;
    private List<String> otts;
    private List<String> categories;
    private String sort = "popularity";
    private int page = 0;
    private int size = 20;
    private Float afterRating;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate afterDate;
    private Long afterId;
}
