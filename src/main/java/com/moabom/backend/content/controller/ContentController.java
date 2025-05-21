package com.moabom.backend.content.controller;

import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.review.model.ReviewEntity;
import com.moabom.backend.content.service.ContentService;
import com.moabom.backend.review.service.ReviewService;
import com.moabom.backend.user.exception.MyPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;
    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    private String extractUserIdOrThrow(String token) {
        String realToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        if (realToken.isEmpty()) {
            throw new MyPageException("유효하지 않은 사용자 토큰입니다.");
        }
        return jwtUtil.extractUserId(realToken);
    }


    @Autowired
    public ContentController(ContentService contentService, ReviewService reviewService, JwtUtil jwtUtil) {
        this.contentService = contentService;
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{contentId}")
    public Map<String, Object> getContentById(
            @PathVariable("contentId") int contentId,
            @RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth) {


        String userId = extractUserIdOrThrow(userIdAuth);

        Map<String, Object> contentDetailMap = new HashMap<>();
        contentDetailMap = contentService.getContentById(contentId, userId);
        return contentDetailMap;
    }

    //한줄평 컨텐츠 별로 가져오기(8개씩)
    @GetMapping("/{contentId}/review")
    public Map<String, Object> getReviewsByContentId(
            @PathVariable("contentId") int contentId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return reviewService.getReviewsByContentId(contentId, page);
    }
}
