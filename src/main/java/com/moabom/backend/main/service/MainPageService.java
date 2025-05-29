package com.moabom.backend.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.moabom.backend.main.model.*;
import com.moabom.backend.main.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final ContentRepository contentRepository;
    private final ContentOttRepository contentOttRepository;
    private final EndOttRepository endOttRepository;

    public Map<String, Object> getMainContents() {
        Map<String, Object> result = new HashMap<>();

        // upcoming: ott별 1개씩
        List<Object[]> rawUpcoming = contentOttRepository.findUpcomingUniqueContents();
        List<UpcomingContentDto> upcoming = rawUpcoming.stream()
            .map(row -> new UpcomingContentDto(
                ((Number) row[0]).intValue(),   
                (String) row[1],                 
                (String) row[2],                 
                (String) row[3],                
                ((java.sql.Date) row[4]).toLocalDate()
            ))
            .collect(Collectors.toList());


        // new
        List<MainContentDto> newContents = contentRepository
        	    .findTop50ByReleaseDateLessThanEqualOrderByReleaseDateDesc(LocalDate.now()) // 충분히 크게 가져오기
        	    .stream()
        	    .map(c -> {
        	        String ottName = c.getContentOtts().stream()
        	            .filter(co -> co.getOtt() != null && co.getOtt().getOttName() != null)
        	            .sorted(Comparator.comparing(co -> co.getContent().getReleaseDate()))
        	            .findFirst()
        	            .map(co -> co.getOtt().getOttName())
        	            .orElse(null);

        	        if (ottName == null) return null;

        	        return new MainContentDto(
        	            c.getContentId(),
        	            c.getTitle(),
        	            c.getPoster(),
        	            ottName
        	        );
        	    })
        	    .filter(Objects::nonNull)
        	    .limit(30) // 여기서 정확히 30개로 제한
        	    .collect(Collectors.toList());


        // rating
        List<MainContentDto> topRated = contentRepository.findTop30ByOrderByRatingDesc().stream()
                .map(c -> new MainContentDto(
                        c.getContentId(),
                        c.getTitle(),
                        c.getPoster(),
                        c.getContentOtts().get(0).getOtt().getOttName()
                )).collect(Collectors.toList());

        // end
        List<MainContentDto> endingSoon = endOttRepository
        	    .findTop50ByEndDateAfterOrderByEndDateAsc(LocalDate.now()) // 50개 정도 넉넉히 뽑고
        	    .stream()
        	    .filter(eo -> eo.getContent() != null && eo.getOtt() != null && eo.getOtt().getOttName() != null)
        	    .collect(Collectors.toMap(
        	        eo -> eo.getContent().getContentId(), // contentId로 중복 제거
        	        eo -> new MainContentDto(
        	            eo.getContent().getContentId(),
        	            eo.getContent().getTitle(),
        	            eo.getContent().getPoster(),
        	            eo.getOtt().getOttName()
        	        ),
        	        (existing, replacement) -> existing, // 중복시 첫 번째 값 유지
        	        LinkedHashMap::new // 순서 유지
        	    ))
        	    .values()
        	    .stream()
        	    .limit(30) // 최종적으로 30개
        	    .collect(Collectors.toList());


        result.put("upcoming", upcoming);
        result.put("new", newContents);
        result.put("rating", topRated);
        result.put("end", endingSoon);

        return result;
    }
}
