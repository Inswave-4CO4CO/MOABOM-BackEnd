package com.moabom.backend.recommend.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendResponseDto {
    private RecommendResultDto first;
    private RecommendResultDto second;
    private RecommendResultDto third;
}
