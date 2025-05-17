package com.moabom.backend.controller.content;

import com.moabom.backend.model.review.ReviewEntity;
import com.moabom.backend.service.content.ContentService;
import com.moabom.backend.service.review.ReviewService;
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

    @Autowired
    public ContentController(ContentService contentService, ReviewService reviewService) {
        this.contentService = contentService;
        this.reviewService = reviewService;
    }

    //작품 상세정보(장르, 배우, 제작진, 줄거리, 시청상태(type) 등...)
    @GetMapping("/{contentId}")
    public Map<String, Object> getContentById(@PathVariable("contentId") int contentId, @RequestParam("userId") String userId) {
        Map<String, Object> contentDetailMap = new HashMap<>();
        contentDetailMap = contentService.getContentById(contentId, userId);
        return contentDetailMap;
    }

    //한줄평 컨텐츠 별로 가져오기(8개씩)
    @GetMapping("/{contentId}/review")
    public List<ReviewEntity> getReviewsByContentId(
            @PathVariable("contentId") int contentId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return reviewService.getReviewsByContentId(contentId, page).getContent();
    }
}
