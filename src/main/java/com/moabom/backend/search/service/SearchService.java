package com.moabom.backend.domain.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.moabom.backend.domain.search.model.Content;
import com.moabom.backend.domain.search.model.ContentDto;
import com.moabom.backend.domain.search.model.PersonDto;
import com.moabom.backend.domain.search.repository.ContentRepository;
import com.moabom.backend.domain.search.repository.GenreRepository;
import com.moabom.backend.domain.search.repository.PersonInfoRepository;
import com.moabom.backend.domain.search.spec.ContentSpec;

@Service
public class SearchService {
	
	@Autowired
	private ContentRepository contentRepository;
	
	@Autowired
	private PersonInfoRepository personInfoRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	public Page<ContentDto> searchWithFilters(
	        String keyword,
	        List<String> genres,
	        List<String> otts,
	        List<String> categories,
	        Pageable pageable
	    ) {
	        Specification<Content> spec = Specification
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

	        Page<Content> page = contentRepository.findAll(spec, pageable);

	        return page.map(c -> ContentDto.builder()
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
	            .genres      (c.getGenres().stream()
	                             .map(g -> g.getGenreName())
	                             .toList())
	            .otts        (c.getOtts().stream()
	                             .map(o -> o.getOttName())
	                             .toList())
	            .build()
	        );
	    }
	
	// 2) /search/cast → cast + person_info JOIN, personName LIKE 검색
	public Page<PersonDto> searchCastByName(String keyword, Pageable pageable) {
        return personInfoRepository
            .findDistinctByCastsIsNotEmptyAndPersonNameContaining(keyword, pageable)
            .map(p -> PersonDto.builder()
                .personId  (p.getPersonId())
                .personName(p.getPersonName())
                .image     (p.getImage())
                .build());
    }

    public Page<PersonDto> searchCrewByName(String keyword, Pageable pageable) {
        return personInfoRepository
            .findDistinctByCrewsIsNotEmptyAndPersonNameContaining(keyword, pageable)
            .map(p -> PersonDto.builder()
                .personId  (p.getPersonId())
                .personName(p.getPersonName())
                .image     (p.getImage())
                .build());
    }
    
    public List<String> getAllGenres() {
    	return genreRepository.findAllGenreNames();
    }
    
    public List<String> getAllCategories() {
    	return contentRepository.findDistinctCategories();
    }
}
