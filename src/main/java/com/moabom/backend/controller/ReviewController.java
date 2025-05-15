package com.moabom.backend.controller;

import com.moabom.backend.model.ReviewEntity;
import com.moabom.backend.repository.ReviewRepository;
import com.moabom.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

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
}
