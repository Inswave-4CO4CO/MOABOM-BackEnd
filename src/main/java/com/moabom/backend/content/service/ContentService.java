package com.moabom.backend.content.service;

import com.moabom.backend.content.exception.ContentNotFoundException;
import com.moabom.backend.content.model.*;
import com.moabom.backend.content.repository.*;
import com.moabom.backend.user.model.MyPagedResultDTO;
import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContentService {
    private final DetailRepository detailRepository;
    private final OttRepository ottRepository;
    private final GenreRepository genreRepository;
    private final WatchRepository watchRepository;
    private final CastRepository castRepository;
    private final CrewRepository crewRepository;
    private final ContentReviewRepository contentReviewRepository;

    @Autowired
    public ContentService(
            DetailRepository detailRepository,
            OttRepository ottRepository,
            GenreRepository genreRepository,
            WatchRepository watchRepository,
            CastRepository castRepository,
            CrewRepository crewRepository, ContentReviewRepository contentReviewRepository) {
        this.detailRepository = detailRepository;
        this.ottRepository = ottRepository;
        this.genreRepository = genreRepository;
        this.watchRepository = watchRepository;
        this.castRepository = castRepository;
        this.crewRepository = crewRepository;
        this.contentReviewRepository = contentReviewRepository;
    }

    //작품 가져오기
    public Map<String, Object> getContentById(int contentId, String userId) {
        Map<String, Object> contentDetailMap = new HashMap<>();
        //컨텐츠 세부정보
        ContentEntity content = detailRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException("[작품 가져오기] contentId(" + contentId + ") 를 찾을 수 없습니다"));
        contentDetailMap.put("content", content);

        //출연자 리스트
        List<CastDTO> castList = castRepository.getCastInfoByContentId(contentId);
        contentDetailMap.put("cast", castList);

        //제작진 리스트
        List<CrewDTO> crewList = crewRepository.getCrewInfoByContentId(contentId);
        contentDetailMap.put("crew", crewList);

        //OTT 리스트
        List<OttDTO> ottList = ottRepository.getOttInfoByContentId(contentId);
        contentDetailMap.put("ott", ottList);

        //장르 리스트
        List<GenreDTO> genreList = genreRepository.getGenreByContentId(contentId);
        List<String> genreNames = genreList.stream()
                .map(GenreDTO::getGenreName)
                .toList();
        contentDetailMap.put("genre", genreNames);

        //시청 상태(type)
        if (userId != null && !userId.isEmpty()) {
            WatchId watchId = new WatchId(userId, contentId);
            Optional<WatchEntity> watchEntity = watchRepository.findById(watchId);

            contentDetailMap.put("type",
                    watchEntity.map(w -> w.getType().toString()).orElse("")
            );

        } else {
            contentDetailMap.put("type", "");
        }

        return contentDetailMap;
    }

    //한줄평 가져오기
    public MyPagedResultDTO<ContentReviewDTO> getReviewByContentId(int contentId, int page){
        return contentReviewRepository.getReviewByContentId(contentId, page, 8);
    }
}
