package com.moabom.backend.service.content;

import com.moabom.backend.exception.content.ContentNotFoundException;
import com.moabom.backend.model.content.*;
import com.moabom.backend.model.watch.WatchEntity;
import com.moabom.backend.model.watch.WatchId;
import com.moabom.backend.repository.content.*;
import com.moabom.backend.repository.watch.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final OttRepository ottRepository;
    private final GenreRepository genreRepository;
    private final WatchRepository watchRepository;
    private final CastRepository castRepository;
    private final CrewRepository crewRepository;

    @Autowired
    public ContentService(
            ContentRepository contentRepository,
            OttRepository ottRepository,
            GenreRepository genreRepository,
            WatchRepository watchRepository,
            CastRepository castRepository,
            CrewRepository crewRepository) {
        this.contentRepository = contentRepository;
        this.ottRepository = ottRepository;
        this.genreRepository = genreRepository;
        this.watchRepository = watchRepository;
        this.castRepository = castRepository;
        this.crewRepository = crewRepository;
    }

    public Map<String, Object> getContentById(int contentId, String userId) {
        Map<String, Object> contentDetailMap = new HashMap<>();
        //컨텐츠 세부정보
        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException("해당 컨텐츠를 찾을 수 없습니다."));
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
}
