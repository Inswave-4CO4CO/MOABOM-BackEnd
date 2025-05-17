package com.moabom.backend.service.review;

import com.moabom.backend.model.review.ReviewEntity;
import com.moabom.backend.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    //리뷰 가져오기 (contentId를 기준으로 / 8개씩)
    public Page<ReviewEntity> getReviewsByContentId(int contentId, int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        return reviewRepository.getReviewByContentId(contentId, pageable);
    }

    //리뷰 찾기 (reviewId를 기준으로)
    public ReviewEntity getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }

    //리뷰 찾기 (contentId, userId를 기준으로)
    public ReviewEntity findByContentIdAndUserId(int contentId, String userId) {
        return reviewRepository.findByContentIdAndUserId(contentId, userId);
    }

    //리뷰추가
    public ReviewEntity insert(ReviewEntity review) {
        int contentId = review.getContentId();
        String userId = review.getUserId();

        ReviewEntity existingReview = reviewRepository.findByContentIdAndUserId(contentId, userId);

        if (existingReview != null) {
            throw new IllegalStateException("사용자 한줄평이 이미 존재합니다");
        }

        return reviewRepository.save(review);
    }

    //리뷰 수정
    @Transactional
    public ReviewEntity update(ReviewEntity review) {
        int reviewId = review.getReviewId();

        Optional<ReviewEntity> existingReviewOptional = reviewRepository.findById(reviewId);

        if (!existingReviewOptional.isPresent()) {
            throw new IllegalArgumentException("한줄평 id " + reviewId + " 를 찾을 수 없습니다");
        }

        ReviewEntity existingReview = existingReviewOptional.get();

        existingReview.setReviewText(review.getReviewText());
        existingReview.setRating(review.getRating());

        return reviewRepository.save(existingReview);
    }


    //리뷰 삭제
    public void delete(int reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("한줄평 id " + reviewId + " 를 찾을 수 없습니다");
        }

        reviewRepository.deleteById(reviewId);
    }
}
