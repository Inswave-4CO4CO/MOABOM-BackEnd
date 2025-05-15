package com.moabom.backend.service;

import com.moabom.backend.model.*;
import com.moabom.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CastAndCrewRepository castRepository;

    @Autowired
    private OttRepository ottRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GenreRepository genreRepository;


    public Map<String, Object> getContentById(int id) {
        Map<String, Object> contentDetailMap = new HashMap<>();

        // 컨텐츠 세부정보
        ContentEntity content = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));

        contentDetailMap.put("content", content);

        // 출연자 리스트
        List<CastAndCrewDTO> castList = castRepository.getCastInfoByContentId(id);
        contentDetailMap.put("cast", castList);

        // 제작진 리스트
        List<CastAndCrewDTO> crewList = castRepository.getCrewInfoByContentId(id);
        contentDetailMap.put("crew", crewList);

        //OTT 리스트
        List<OttDTO> ottList = ottRepository.getOttInfoByContentId(id);
        contentDetailMap.put("ott", ottList);

        //리뷰 리스트
        List<ReviewEntity> reviewList = reviewRepository.findByContentId(id);
        contentDetailMap.put("review", reviewList);

        //장르 리스트
        List<GenreDTO> genreList = genreRepository.getGenreByContentId(id);
        ContentEntity e = new ContentEntity();

        List<String> genreNames = genreList.stream()
                .map(GenreDTO::getGenreName)
                .toList();

        contentDetailMap.put("genre", genreNames);
        return contentDetailMap;
    }
}
