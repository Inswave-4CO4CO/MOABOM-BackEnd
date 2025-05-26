package com.moabom.backend.recommend.model;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendResultDto {
	private String ottName;
    private List<RecommendPlanDto> plan;
    private List<RecommendContentDto> contents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendPlanDto {
        private Long ottId;
        private String planName;
        private Integer price;
    }
}