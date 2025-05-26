package com.moabom.backend.recommend.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendContentDto {
    private Long contentId;
    private String title;
    private String poster;
    private String ottName;
}
