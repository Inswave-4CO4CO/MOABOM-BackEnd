package com.moabom.backend.content.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentReviewDTO {
    private Integer reviewId;
    private String reviewText;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Float rating;
    private Integer contentId;
    private String userId;
    private String nickName;
    private String userImage;
}
