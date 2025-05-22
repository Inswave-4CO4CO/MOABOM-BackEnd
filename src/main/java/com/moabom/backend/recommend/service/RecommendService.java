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

    public RecommendResponseDto getOttRecommendations(String userId) {
        List<RecommendWatch> wishList = watchRepository.findByUserIdAndType(userId, "보고싶다");
        Map<Long, List<RecommendContent>> ottContentMap = new HashMap<>();

        for (RecommendWatch watch : wishList) {
            RecommendContent content = contentRepository.findById(watch.getContentId()).orElse(null);
            if (content != null) {
                List<RecommendContentOtt> contentOtts = contentOttRepository.findByRecommendContent(content);
                for (RecommendContentOtt contentOtt : contentOtts) {
                    Long ottId = contentOtt.getRecommendOtt().getOttId();
                    ottContentMap.computeIfAbsent(ottId, k -> new ArrayList<>()).add(content);
                }
            }
        }

        List<Map.Entry<Long, List<RecommendContent>>> topOtts = ottContentMap.entrySet().stream()
            .sorted((a, b) -> b.getValue().size() - a.getValue().size())
            .limit(3)
            .toList();

        RecommendResponseDto response = new RecommendResponseDto();

        for (int i = 0; i < topOtts.size(); i++) {
            Long ottId = topOtts.get(i).getKey();
            String ottName = ottRepository.findById(ottId).map(RecommendOtt::getOttName).orElse("Unknown OTT");

            List<RecommendPlanDetails> plans = planDetailsRepository.findByRecommendOtt_OttId(ottId);
            List<RecommendResultDto.RecommendPlanDto> planDtos = plans.stream()
                .map(plan -> new RecommendResultDto.RecommendPlanDto(ottId, plan.getPlanName(), plan.getPrice()))
                .collect(Collectors.toList());

            List<RecommendContentDto> contentDtos = topOtts.get(i).getValue().stream()
                .map(c -> new RecommendContentDto(c.getContentId(), c.getTitle(), c.getPoster()))
                .collect(Collectors.toList());

            RecommendResultDto result = new RecommendResultDto(ottName, planDtos, contentDtos);

            if (i == 0) response.setFirst(result);
            else if (i == 1) response.setSecond(result);
            else response.setThird(result);
        }

        return response;
    }
}
