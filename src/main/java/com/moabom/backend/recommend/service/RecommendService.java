package com.moabom.backend.recommend.service;

import com.moabom.backend.recommend.model.*;
import com.moabom.backend.recommend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendWatchRepository watchRepository;
    private final RecommendContentRepository contentRepository;
    private final RecommendContentOttRepository contentOttRepository;
    private final RecommendOttRepository ottRepository;
    private final RecommendPlanDetailsRepository planDetailsRepository;

    public RecommendResponseDto getOttRecommendations(String userId, RecommendRequestDto requestDto) {
        
        // 1. 요청된 콘텐츠 리스트
        List<RecommendContentDto> requestedContents = requestDto.getContents();

        // 2. 요청된 콘텐츠 중 '보고싶다' 목록 필터링 (기존 유지)
        List<Long> selectedContentIds = requestedContents.stream()
                .map(RecommendContentDto::getContentId)
                .toList();

        List<RecommendWatch> wishList = watchRepository.findByUserIdAndType(userId, "WANT");
        List<RecommendWatch> filtered = wishList.stream()
                .filter(w -> selectedContentIds.contains(w.getContentId()))
                .toList();

        // 3. OTT 이름별로 콘텐츠 그룹핑 (요청의 ottName 필드 기반)
        Map<String, Set<RecommendContentDto>> ottNameToContents = new LinkedHashMap<>(); // 순서 유지 위해 LinkedHashMap 사용

        for (RecommendContentDto contentDto : requestedContents) {
            String ottNamesStr = contentDto.getOttName(); // 예: "넷플릭스, U+모바일tv, 웨이브"
            if (ottNamesStr == null || ottNamesStr.isEmpty()) continue;

            // ',' 기준으로 OTT 이름 분리 및 앞뒤 공백 제거
            String[] ottNames = ottNamesStr.split(",");
            for (String rawOttName : ottNames) {
                String ottName = rawOttName.trim();
                // Set으로 중복 제거하며 콘텐츠 추가
                ottNameToContents.computeIfAbsent(ottName, k -> new LinkedHashSet<>()).add(contentDto);
            }
        }

        // 4. OTT 이름 기준으로 요금제(plan) 조회 및 RecommendResultDto 생성
        RecommendResponseDto response = new RecommendResponseDto();

        // ottNameToContents를 작품 수 기준 내림차순, 같으면 ottName 오름차순으로 정렬
        List<Map.Entry<String, Set<RecommendContentDto>>> sortedEntries = ottNameToContents.entrySet().stream()
                .sorted((e1, e2) -> {
                    int sizeCompare = Integer.compare(e2.getValue().size(), e1.getValue().size());
                    return sizeCompare != 0 ? sizeCompare : e1.getKey().compareTo(e2.getKey());
                })
                .collect(Collectors.toList());


        // OTT별 추천 결과를 최대 3개까지만 세팅 (first, second, third)
        int count = 0;
        for (Map.Entry<String, Set<RecommendContentDto>> entry : sortedEntries) {
            if (count >= 3) break;

            String ottName = entry.getKey();

            Optional<RecommendOtt> ottOpt = ottRepository.findByOttName(ottName);
            if (ottOpt.isEmpty()) continue;

            Long ottId = ottOpt.get().getOttId();

            List<RecommendPlanDetails> plans = planDetailsRepository.findByRecommendOtt_OttId(ottId);
            List<RecommendResultDto.RecommendPlanDto> planDtos = plans.stream()
                    .map(plan -> new RecommendResultDto.RecommendPlanDto(ottId, plan.getPlanName(), plan.getPrice()))
                    .collect(Collectors.toList());

            List<RecommendContentDto> contentDtos = new ArrayList<>(entry.getValue());

            RecommendResultDto result = new RecommendResultDto(ottName, planDtos, contentDtos);

            if (count == 0) response.setFirst(result);
            else if (count == 1) response.setSecond(result);
            else if (count == 2) response.setThird(result);

            count++;
        }

        // 만약 추천 OTT가 3개 미만이라면 null로 유지됨 (기존과 동일)

        return response;
    }

}