package com.moabom.backend.recommend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRequestDto {
    private List<RecommendContentDto> contents;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
    	private Long contentId;
    	private String title;
    	private String poster;
    }
}
