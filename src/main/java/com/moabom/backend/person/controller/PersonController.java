// PersonController.java
package com.moabom.backend.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moabom.backend.person.model.PersonDetailDto;
import com.moabom.backend.person.model.PersonFilmographyDto;
import com.moabom.backend.person.model.PersonDto;
import com.moabom.backend.person.service.PersonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private PersonService personService;
    
    @GetMapping("/{personId}")
    public ResponseEntity<Map<String, Object>> getPersonInfo(
            @PathVariable("personId") Integer personId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "otts", required = false) String otts) {
        
        final List<String> ottList;
        if (otts != null && !otts.trim().isEmpty()) {
            ottList = Arrays.asList(otts.split(","));
        } else {
            ottList = null;
        }
        
        PersonDetailDto personDetail = personService.getPersonDetail(personId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("personId", personDetail.getPersonId());
        response.put("personName", personDetail.getPersonName());
        response.put("image", personDetail.getImage());
        
        // 역할별로 필모그래피 분류
        List<PersonFilmographyDto> allFilmography = personDetail.getFilmography();
        
        // OTT 필터링 적용
        if (ottList != null && !ottList.isEmpty()) {
            allFilmography = allFilmography.stream()
                    .filter(f -> hasMatchingOtt(f, ottList))
                    .collect(Collectors.toList());
        }
        
        // 페이지네이션을 위한 분리
        Map<String, List<Map<String, Object>>> filmographyByRole = new HashMap<>();
        Map<String, Boolean> hasMoreByRole = new HashMap<>();
        
        // 배우로서 참여한 작품 - category와 releaseYear 필드를 제외
        List<PersonFilmographyDto> actorFilmography = allFilmography.stream()
                .filter(f -> "cast".equals(f.getRoleType()))
                .collect(Collectors.toList());
        
        List<Map<String, Object>> paginatedActorFilmography = paginateFilmography(actorFilmography, page);
        boolean hasMoreActors = actorFilmography.size() > (page + 1) * PAGE_SIZE;
        
        // 감독으로서 참여한 작품 - category와 releaseYear 필드를 제외
        List<PersonFilmographyDto> directorFilmography = allFilmography.stream()
                .filter(f -> "crew".equals(f.getRoleType()) && "DIR".equals(f.getRole()))
                .collect(Collectors.toList());
        
        List<Map<String, Object>> paginatedDirectorFilmography = paginateFilmography(directorFilmography, page);
        boolean hasMoreDirectors = directorFilmography.size() > (page + 1) * PAGE_SIZE;
        
        // 작가로서 참여한 작품 - category와 releaseYear 필드를 제외
        List<PersonFilmographyDto> writerFilmography = allFilmography.stream()
                .filter(f -> "crew".equals(f.getRoleType()) && "WRI".equals(f.getRole()))
                .collect(Collectors.toList());
        
        List<Map<String, Object>> paginatedWriterFilmography = paginateFilmography(writerFilmography, page);
        boolean hasMoreWriters = writerFilmography.size() > (page + 1) * PAGE_SIZE;
        
        // 결과 설정
        filmographyByRole.put("actor", paginatedActorFilmography);
        filmographyByRole.put("director", paginatedDirectorFilmography);
        filmographyByRole.put("writer", paginatedWriterFilmography);
        
        hasMoreByRole.put("actor", hasMoreActors);
        hasMoreByRole.put("director", hasMoreDirectors);
        hasMoreByRole.put("writer", hasMoreWriters);
        
        response.put("filmography", filmographyByRole);
        response.put("hasMore", hasMoreByRole);
        response.put("currentPage", page);
        
        return ResponseEntity.ok(response);
    }
    
    // OTT 필터링 함수
    private boolean hasMatchingOtt(PersonFilmographyDto filmography, List<String> ottNames) {
        if (filmography.getOtts() == null || filmography.getOtts().isEmpty()) {
            return false;
        }
        
        return filmography.getOtts().stream()
                .anyMatch(ott -> ottNames.contains(ott.getOttName()));
    }
    
    // 페이지네이션 함수
    private List<Map<String, Object>> paginateFilmography(List<PersonFilmographyDto> filmography, int page) {
        int startIndex = page * PAGE_SIZE;
        if (startIndex >= filmography.size()) {
            return new ArrayList<>();
        }
        
        int endIndex = Math.min(startIndex + PAGE_SIZE, filmography.size());
        return filmography.subList(startIndex, endIndex).stream()
                .map(this::convertToMapWithoutCategoryAndReleaseYear)
                .collect(Collectors.toList());
    }
    
    // DTO를 Map으로 변환하면서 category와 releaseYear 필드를 제외
    private Map<String, Object> convertToMapWithoutCategoryAndReleaseYear(PersonFilmographyDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", dto.getContentId());
        map.put("title", dto.getTitle());
        map.put("roleType", dto.getRoleType());
        map.put("role", dto.getRole());
        map.put("poster", dto.getPoster());
        map.put("otts", dto.getOtts());
        // category와 releaseYear는 포함하지 않음
        return map;
    }
} 