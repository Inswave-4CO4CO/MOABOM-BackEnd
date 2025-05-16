package com.moabom.backend.service.review;

import com.moabom.backend.model.review.ReviewEntity;
import com.moabom.backend.repository.review.ReviewRepository;
import com.moabom.backend.repository.watch.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //리뷰추가
    public void insert(ReviewEntity review) {
        reviewRepository.save(review);
    }

    //리뷰 수정
    public void update(ReviewEntity review) {
        reviewRepository.save(review);
    }

    //리뷰 삭제
    public void delete(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
