// PersonService.java
package com.moabom.backend.person.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moabom.backend.person.model.OttDto;
import com.moabom.backend.person.model.Person;
import com.moabom.backend.person.model.PersonCast;
import com.moabom.backend.person.model.PersonContent;
import com.moabom.backend.person.model.PersonCrew;
import com.moabom.backend.person.model.PersonDetailDto;
import com.moabom.backend.person.model.PersonDto;
import com.moabom.backend.person.model.PersonFilmographyDto;
import com.moabom.backend.person.model.PersonOtt;
import com.moabom.backend.person.model.PersonOttMaster;
import com.moabom.backend.person.repository.PersonCastRepository;
import com.moabom.backend.person.repository.PersonContentRepository;
import com.moabom.backend.person.repository.PersonCrewRepository;
import com.moabom.backend.person.repository.PersonOttMasterRepository;
import com.moabom.backend.person.repository.PersonOttRepository;
import com.moabom.backend.person.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PersonCastRepository castRepository;
    
    @Autowired
    private PersonCrewRepository crewRepository;
    
    @Autowired
    private PersonContentRepository contentRepository;
    
    @Autowired
    private PersonOttRepository ottRepository;
    
    @Autowired
    private PersonOttMasterRepository ottMasterRepository;
    
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
        
        // 모든 contentId 추출
        List<Integer> castContentIds = casts.stream()
                .map(PersonCast::getContentId)
                .collect(Collectors.toList());
        
        List<Integer> crewContentIds = crews.stream()
                .map(PersonCrew::getContentId)
                .collect(Collectors.toList());
        
        List<Integer> allContentIds = new ArrayList<>(castContentIds);
        allContentIds.addAll(crewContentIds);
        
        // Content 정보 조회
        List<PersonContent> contents = contentRepository.findByContentIds(allContentIds);
        Map<Integer, PersonContent> contentMap = contents.stream()
                .collect(Collectors.toMap(PersonContent::getContentId, Function.identity()));
        
        // OTT 정보 조회
        List<PersonOtt> otts = ottRepository.findByContentIds(allContentIds);
        
        // contentId별 OTT 목록
        Map<Integer, List<PersonOtt>> contentOttsMap = otts.stream()
                .collect(Collectors.groupingBy(PersonOtt::getContentId));
        
        // OTT 마스터 정보 조회
        List<Integer> ottIds = otts.stream()
                .map(PersonOtt::getOttId)
                .distinct()
                .collect(Collectors.toList());
        
        List<PersonOttMaster> ottMasters = ottMasterRepository.findByOttIds(ottIds);
        Map<Integer, PersonOttMaster> ottMasterMap = ottMasters.stream()
                .collect(Collectors.toMap(PersonOttMaster::getOttId, Function.identity()));
        
        List<PersonFilmographyDto> filmography = new ArrayList<>();
        filmography.addAll(convertCastsToFilmographyDtoList(casts, contentMap, contentOttsMap, ottMasterMap));
        filmography.addAll(convertCrewsToFilmographyDtoList(crews, contentMap, contentOttsMap, ottMasterMap));
        
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
    
    private List<PersonFilmographyDto> convertCastsToFilmographyDtoList(
            List<PersonCast> casts, 
            Map<Integer, PersonContent> contentMap,
            Map<Integer, List<PersonOtt>> contentOttsMap,
            Map<Integer, PersonOttMaster> ottMasterMap) {
        return casts.stream()
                .map(c -> {
                    PersonContent content = contentMap.get(c.getContentId());
                    String title = "";
                    String category = "";
                    String poster = "";
                    Integer releaseYear = 0;
                    List<OttDto> ottDtos = new ArrayList<>();
                    
                    if (content != null) {
                        title = content.getTitle();
                        category = content.getCategory();
                        poster = content.getPoster();
                        // 날짜 형식이 "YYYY-MM-DD"라고 가정
                        String releaseDate = content.getReleaseDate();
                        if (releaseDate != null && releaseDate.length() >= 4) {
                            try {
                                releaseYear = Integer.parseInt(releaseDate.substring(0, 4));
                            } catch (NumberFormatException e) {
                                // 파싱 실패시 기본값 0 유지
                            }
                        }
                        
                        // OTT 정보 설정
                        List<PersonOtt> contentOtts = contentOttsMap.getOrDefault(c.getContentId(), new ArrayList<>());
                        ottDtos = contentOtts.stream()
                                .map(ott -> {
                                    PersonOttMaster ottMaster = ottMasterMap.get(ott.getOttId());
                                    if (ottMaster != null) {
                                        return OttDto.builder()
                                                .ottId(ottMaster.getOttId())
                                                .ottName(ottMaster.getOttName())
                                                .url(ott.getUrl())
                                                .build();
                                    }
                                    return null;
                                })
                                .filter(ott -> ott != null)
                                .collect(Collectors.toList());
                    }
                    
                    return PersonFilmographyDto.builder()
                            .contentId(c.getContentId())
                            .title(title)
                            .category(category)
                            .roleType("cast")
                            .role(c.getRole().toString())
                            .poster(poster)
                            .releaseYear(releaseYear)
                            .otts(ottDtos)
                            .build();
                })
                .collect(Collectors.toList());
    }
    
    private List<PersonFilmographyDto> convertCrewsToFilmographyDtoList(
            List<PersonCrew> crews, 
            Map<Integer, PersonContent> contentMap,
            Map<Integer, List<PersonOtt>> contentOttsMap,
            Map<Integer, PersonOttMaster> ottMasterMap) {
        
        if (crews == null || crews.isEmpty()) {
            return new ArrayList<>();
        }

        // contentId로 그룹화
        Map<Integer, List<PersonCrew>> crewsByContentId = crews.stream()
                .collect(Collectors.groupingBy(PersonCrew::getContentId));

        List<PersonFilmographyDto> filmographyList = new ArrayList<>();

        for (Map.Entry<Integer, List<PersonCrew>> entry : crewsByContentId.entrySet()) {
            Integer contentId = entry.getKey();
            List<PersonCrew> contentCrews = entry.getValue();
            
            if (contentCrews.isEmpty()) continue;

            // 대표 역할 결정 (DIR > WRI)
            // com.moabom.backend.person.model.PersonCrewRole enum을 사용한다고 가정
            String representativeRoleStr = contentCrews.stream()
                    .map(PersonCrew::getRole)
                    .min((r1, r2) -> {
                        // com.moabom.backend.person.model.PersonCrewRole enum 사용
                        if (r1 == com.moabom.backend.person.model.PersonCrewRole.DIR) return -1;
                        if (r2 == com.moabom.backend.person.model.PersonCrewRole.DIR) return 1;
                        if (r1 == com.moabom.backend.person.model.PersonCrewRole.WRI) return -1;
                        if (r2 == com.moabom.backend.person.model.PersonCrewRole.WRI) return 1;
                        return 0;
                    })
                    .map(Enum::toString) // Enum을 문자열로 변환
                    .orElse(contentCrews.get(0).getRole().toString()); // 우선순위 없으면 첫 번째 역할

            PersonContent content = contentMap.get(contentId);
            String title = "";
            String category = "";
            String poster = "";
            Integer releaseYear = 0;
            List<OttDto> ottDtos = new ArrayList<>();
            
            if (content != null) {
                title = content.getTitle();
                category = content.getCategory();
                poster = content.getPoster();
                String releaseDate = content.getReleaseDate();
                if (releaseDate != null && releaseDate.length() >= 4) {
                    try {
                        releaseYear = Integer.parseInt(releaseDate.substring(0, 4));
                    } catch (NumberFormatException e) {
                        // 파싱 실패시 기본값 0 유지
                    }
                }
                
                List<PersonOtt> contentOtts = contentOttsMap.getOrDefault(contentId, new ArrayList<>());
                ottDtos = contentOtts.stream()
                        .map(ott -> {
                            PersonOttMaster ottMaster = ottMasterMap.get(ott.getOttId());
                            if (ottMaster != null) {
                                return OttDto.builder()
                                        .ottId(ottMaster.getOttId())
                                        .ottName(ottMaster.getOttName())
                                        .url(ott.getUrl())
                                        .build();
                            }
                            return null;
                        })
                        .filter(ott -> ott != null)
                        .collect(Collectors.toList());
            }
            
            filmographyList.add(PersonFilmographyDto.builder()
                    .contentId(contentId)
                    .title(title)
                    .category(category)
                    .roleType("crew")
                    .role(representativeRoleStr)
                    .poster(poster)
                    .releaseYear(releaseYear)
                    .otts(ottDtos)
                    .build());
        }
        return filmographyList;
    }
} 