package com.moabom.backend.search.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private List<SearchContentDto> content;
    private List<SearchPersonDto>  person;
    private List<String> allGenres;
    private List<String> allCategories;
    private boolean hasNext;
}
