package com.moabom.backend.content.controller;

import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.review.model.ReviewEntity;
import com.moabom.backend.content.service.ContentService;
import com.moabom.backend.review.service.ReviewService;
import com.moabom.backend.user.exception.MyPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/content")

public class ContentController {
    private final ContentService contentService;
    private final ReviewService reviewService;


    @Autowired
    public ContentController(ContentService contentService, ReviewService reviewService) {
        this.contentService = contentService;
        this.reviewService = reviewService;
    }

    @GetMapping("/{contentId}")
    public Map<String, Object> getContentById(
            @PathVariable("contentId") int contentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return contentService.getContentById(contentId, userDetails.getUsername());
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
