package com.moabom.backend.review.controller;

import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.review.model.ReviewEntity;
import com.moabom.backend.review.service.ReviewService;
import com.moabom.backend.user.exception.MyPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 (필요 없을 수도 있지만 혹시 모르니 남겨둠)
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ReviewController(ReviewService reviewService, JwtUtil jwtUtil) {
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }

    //한줄평 추가
    @PostMapping
    public ResponseEntity<ReviewEntity> insert(@RequestBody ReviewEntity review, @AuthenticationPrincipal UserDetails userDetails) {
        review.setUserId(userDetails.getUsername());

        ReviewEntity savedReview = reviewService.insert(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    //한줄평 찾기 (reviewId를 기준으로)
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable("reviewId") int reviewId) {
        ReviewEntity review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    //한줄평 찾기 (contentId, userId를 기준으로)
    @GetMapping
    public ResponseEntity<?> findByContentIdAndUserId (
            @RequestParam(value = "contentId", defaultValue = "") int contentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        ReviewEntity review = reviewService.findByContentIdAndUserId(contentId, userDetails.getUsername());

        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[한줄평 찾기] 해당 한줄평이 없습니다");
        }

        return ResponseEntity.ok(review);
    }

    //한줄평 수정
    @PutMapping
    public ResponseEntity<ReviewEntity> update(@RequestBody ReviewEntity review, @AuthenticationPrincipal UserDetails userDetails) {
        review.setUserId(userDetails.getUsername());
        ReviewEntity updatedReview = reviewService.update(review);

        return ResponseEntity.ok(updatedReview);
    }

    //한줄평 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable("reviewId") int reviewId) {
        reviewService.delete(reviewId);
        return ResponseEntity.ok().body("[한줄평 삭제] 해당 한줄평이 삭제되었습니다");
    }
}
