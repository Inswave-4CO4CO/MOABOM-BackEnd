// SearchService.java
package com.moabom.backend.search.service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.moabom.backend.search.model.SearchContent;
import com.moabom.backend.search.model.SearchContentDto;
import com.moabom.backend.search.model.SearchPersonDto;
import com.moabom.backend.search.repository.SearchRepository;
import com.moabom.backend.search.repository.SearchGenreRepository;
import com.moabom.backend.search.repository.PersonInfoRepository;
import com.moabom.backend.search.spec.ContentSpec;

@Service
public class SearchService {
    
    @Autowired
    private SearchRepository searchRepository;
    
    @Autowired
    private PersonInfoRepository personInfoRepository;
    
    @Autowired
    private SearchGenreRepository searchGenreRepository;
    
    public Page<SearchContentDto> searchWithFilters(
            String keyword,
            List<String> genres,
            List<String> otts,
            List<String> categories,
            Pageable pageable
        ) {
            Specification<SearchContent> spec = Specification
                .where(ContentSpec.titleContains(keyword));

            if (genres != null && !genres.isEmpty()) {
                spec = spec.and(ContentSpec.genreIn(genres));
            }
            if (otts != null && !otts.isEmpty()) {
                spec = spec.and(ContentSpec.ottIn(otts));
            }
            if (categories != null && !categories.isEmpty()) {
                spec = spec.and(ContentSpec.categoryIn(categories));
            }

            Page<SearchContent> page = searchRepository.findAll(spec, pageable);

            return page.map(c -> SearchContentDto.builder()
                .contentId   (c.getContentId())
                .title       (c.getTitle())
                .description (c.getDescription())
                .releaseDate (c.getReleaseDate())
                .category    (c.getCategory())
                .runningTime (c.getRunningTime())
                .image       (c.getImage())
                .rating      (c.getRating())
                .ageRating   (c.getAgeRating())
                .madeIn      (c.getMadeIn())
                .poster      (c.getPoster())
                .imdbRating  (c.getImdbRating())
                .genres      (c.getSearchGenres().stream()
                                 .map(g -> g.getGenreName())
                                 .toList())
                .otts        (c.getSearchOtts().stream()
                                 .map(o -> o.getOttName())
                                 .toList())
                .build()
            );
        }
    
    // 2) /search/cast → cast + person_info JOIN, personName LIKE 검색
    public Page<SearchPersonDto> searchCastByName(String keyword, Pageable pageable) {
        return personInfoRepository
            .findDistinctBySearchCastsIsNotEmptyAndPersonNameContaining(keyword, pageable)
            .map(p -> SearchPersonDto.builder()
                .personId  (p.getPersonId())
                .personName(p.getPersonName())
                .image     (p.getImage())
                .build());
    }

    public Page<SearchPersonDto> searchCrewByName(String keyword, Pageable pageable) {
        return personInfoRepository
            .findDistinctBySearchCrewsIsNotEmptyAndPersonNameContaining(keyword, pageable)
            .map(p -> SearchPersonDto.builder()
                .personId  (p.getPersonId())
                .personName(p.getPersonName())
                .image     (p.getImage())
                .build());
    }
    
    public List<String> getAllGenres() {
        return searchGenreRepository.findAllGenreNames();
    }
    
    public List<String> getAllCategories() {
        return searchRepository.findDistinctCategories().stream()
            .flatMap(category -> Arrays.stream(category.split(",")))
            .map(String::trim)
            .distinct()
            .collect(Collectors.toList());
    }
}
