package com.moabom.backend.review.service;

import com.moabom.backend.review.model.ReviewEntity;
import com.moabom.backend.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //한줄평 가져오기 (contentId를 기준으로 / 8개씩)
    public Map<String, Object> getReviewsByContentId(int contentId, int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ReviewEntity> reviewPage = reviewRepository.getReviewByContentId(contentId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewPage.getContent());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("currentPage", page);
        response.put("reviewCount", reviewPage.getTotalElements());

        return response;
    }

    //한줄평 찾기 (reviewId를 기준으로)
    public ReviewEntity getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("[한줄평 찾기] reviewId(" + reviewId + ") 가 존재하지 않습니다"));
    }

    //한줄평 찾기 (contentId, userId를 기준으로)
    public ReviewEntity findByContentIdAndUserId(int contentId, String userId) {
        ReviewEntity review = reviewRepository.findByContentIdAndUserId(contentId, userId);
        if (review == null) {
            throw new IllegalArgumentException("[한줄평 찾기] (contentId=" + contentId + " , userId=" + userId + ") 가 존재하지 않습니다");
        }
        return review;
    }

    //한줄평 추가
    public ReviewEntity insert(ReviewEntity review) {
        int contentId = review.getContentId();
        String userId = review.getUserId();

        ReviewEntity existingReview = reviewRepository.findByContentIdAndUserId(contentId, userId);

        if (existingReview != null) {
            throw new IllegalStateException("[한줄평 추가] (contentId=" + existingReview.getContentId() + " , userId=" + existingReview.getUserId() + ") 가 이미 존재합니다");
        }

        return reviewRepository.save(review);
    }

    //한줄평 수정
    @Transactional
    public ReviewEntity update(ReviewEntity review) {
        int reviewId = review.getReviewId();

        Optional<ReviewEntity> existingReviewOptional = reviewRepository.findById(reviewId);

        if (!existingReviewOptional.isPresent()) {
            throw new IllegalArgumentException("[한줄평 수정] reviewId(" + reviewId + ") 를 찾을 수 없습니다");
        }

        ReviewEntity existingReview = existingReviewOptional.get();
        existingReview.setReviewText(review.getReviewText());
        existingReview.setRating(review.getRating());

        return reviewRepository.save(existingReview);
    }


    //한줄평 삭제
    public void delete(int reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("[한줄평 삭제] reviewId(" + reviewId + ") 를 찾을 수 없습니다");
        }

        reviewRepository.deleteById(reviewId);
    }
}
