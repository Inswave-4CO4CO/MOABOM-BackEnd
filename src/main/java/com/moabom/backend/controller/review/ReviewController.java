package com.moabom.backend.controller.review;

import com.moabom.backend.model.review.ReviewEntity;
import com.moabom.backend.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 (필요 없을 수도 있지만 혹시 모르니 남겨둠)
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //리뷰추가
    @PostMapping
    public ResponseEntity<ReviewEntity> insert(@RequestBody ReviewEntity review) {
        ReviewEntity savedReview = reviewService.insert(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    // 리뷰 찾기 (reviewId를 기준으로)
    @GetMapping("/{reviewId}") // reviewId는 경로 변수로!
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable("reviewId") int reviewId) { // 성공 시 ReviewEntity 반환
        ReviewEntity review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // 리뷰 찾기 (contentId, userId를 기준으로)
    @GetMapping
    public ResponseEntity<?> findByContentIdAndUserId (
            @RequestParam("contentId") int contentId,
            @RequestParam("userId") String userId) { // ResponseEntity<?> 반환

        ReviewEntity review = reviewService.findByContentIdAndUserId(contentId, userId);

        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found for this content and user.");
        }

        return ResponseEntity.ok(review);
    }


    // 리뷰 수정
    @PutMapping
    public ResponseEntity<ReviewEntity> update(@RequestBody ReviewEntity review) {
        ReviewEntity updatedReview = reviewService.update(review);

        return ResponseEntity.ok(updatedReview);
    }

    //한줄평 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable("reviewId") int reviewId) { // 성공 시 String 메시지 반환
        reviewService.delete(reviewId);
        return ResponseEntity.ok().body("한줄평이 삭제되었습니다");
    }
}
