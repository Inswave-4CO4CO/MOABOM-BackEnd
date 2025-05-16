package com.moabom.backend.controller.review;

import com.moabom.backend.model.review.ReviewEntity;
import com.moabom.backend.service.content.ContentService;
import com.moabom.backend.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping ("/review")
    public void insert(@RequestBody ReviewEntity review) {
        reviewService.insert(review);
    }

    @GetMapping ("/review/{reviewId}")
    public ReviewEntity getReviewById(@PathVariable("reviewId") int id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping ("/review")
    public void update(@RequestBody ReviewEntity review) {
        reviewService.update(review);
    }

    @DeleteMapping("/review/{reviewId}")
    public void delete(@PathVariable("reviewId") int id) {
        reviewService.delete(id);
    }

    @GetMapping("/content/{contentId}/review")
    public List<ReviewEntity> getReviewsByContentId(
            @PathVariable("contentId") int contentId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return reviewService.getReviewsByContentId(contentId, page).getContent();
    }
}