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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @GetMapping("/{personId}")
    public ResponseEntity<Map<String, Object>> getPersonInfo(@PathVariable Integer personId) {
        PersonDetailDto personDetail = personService.getPersonDetail(personId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("personId", personDetail.getPersonId());
        response.put("personName", personDetail.getPersonName());
        response.put("image", personDetail.getImage());
        
        // 역할별로 필모그래피 분류
        List<PersonFilmographyDto> allFilmography = personDetail.getFilmography();
        
        // 배우로서 참여한 작품
        List<PersonFilmographyDto> asActor = allFilmography.stream()
                .filter(f -> "cast".equals(f.getRoleType()))
                .collect(Collectors.toList());
        
        // 감독으로서 참여한 작품
        List<PersonFilmographyDto> asDirector = allFilmography.stream()
                .filter(f -> "crew".equals(f.getRoleType()) && "DIR".equals(f.getRole()))
                .collect(Collectors.toList());
        
        // 작가로서 참여한 작품
        List<PersonFilmographyDto> asWriter = allFilmography.stream()
                .filter(f -> "crew".equals(f.getRoleType()) && "WRI".equals(f.getRole()))
                .collect(Collectors.toList());
        
        Map<String, Object> filmographyByRole = new HashMap<>();
        filmographyByRole.put("actor", asActor);
        filmographyByRole.put("director", asDirector);
        filmographyByRole.put("writer", asWriter);
        
        response.put("filmography", filmographyByRole);
        
        return ResponseEntity.ok(response);
    }
} 