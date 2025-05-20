// PersonService.java
package com.moabom.backend.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moabom.backend.person.model.Person;
import com.moabom.backend.person.model.PersonCast;
import com.moabom.backend.person.model.PersonCrew;
import com.moabom.backend.person.model.PersonDetailDto;
import com.moabom.backend.person.model.PersonDto;
import com.moabom.backend.person.model.PersonFilmographyDto;
import com.moabom.backend.person.repository.PersonCastRepository;
import com.moabom.backend.person.repository.PersonCrewRepository;
import com.moabom.backend.person.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PersonCastRepository castRepository;
    
    @Autowired
    private PersonCrewRepository crewRepository;
    
    @Transactional(readOnly = true)
    public Page<PersonDto> searchPersons(String keyword, Pageable pageable) {
        return personRepository.findByPersonNameContaining(keyword, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public PersonDetailDto getPersonDetail(Integer personId) {
        Person person = personRepository.findByPersonId(personId)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + personId));
        
        List<PersonCast> casts = castRepository.findByPerson(person);
        List<PersonCrew> crews = crewRepository.findByPerson(person);
        
        List<PersonFilmographyDto> filmography = new ArrayList<>();
        filmography.addAll(convertCastsToFilmographyDtoList(casts));
        filmography.addAll(convertCrewsToFilmographyDtoList(crews));
        
        return PersonDetailDto.builder()
                .personId(person.getPersonId())
                .personName(person.getPersonName())
                .image(person.getImage())
                .filmography(filmography)
                .build();
    }
    
    private PersonDto convertToDto(Person person) {
        return PersonDto.builder()
                .personId(person.getPersonId())
                .personName(person.getPersonName())
                .image(person.getImage())
                .build();
    }
    
    private List<PersonFilmographyDto> convertCastsToFilmographyDtoList(List<PersonCast> casts) {
        return casts.stream()
                .map(c -> PersonFilmographyDto.builder()
                        .contentId(c.getContentId())
                        .title("") // Content 정보는 별도 서비스에서 가져와야 함
                        .category("")
                        .roleType("cast")
                        .role(c.getRole().toString())
                        .poster("")
                        .releaseYear(0) // 실제 구현시 Content 서비스에서 가져와야 함
                        .build())
                .collect(Collectors.toList());
    }
    
    private List<PersonFilmographyDto> convertCrewsToFilmographyDtoList(List<PersonCrew> crews) {
        return crews.stream()
                .map(c -> PersonFilmographyDto.builder()
                        .contentId(c.getContentId())
                        .title("") // Content 정보는 별도 서비스에서 가져와야 함
                        .category("")
                        .roleType("crew")
                        .role(c.getRole().toString())
                        .poster("")
                        .releaseYear(0) // 실제 구현시 Content 서비스에서 가져와야 함
                        .build())
                .collect(Collectors.toList());
    }
} 