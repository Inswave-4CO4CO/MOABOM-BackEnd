package com.moabom.backend.service;

import com.moabom.backend.model.ReviewEntity;
import com.moabom.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void insert(ReviewEntity review) {
        reviewRepository.save(review);
    }

    public ReviewEntity getReviewById(int id) {
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }

    public void update(ReviewEntity review) {
        reviewRepository.save(review);
    }

    public void delete(int id) {
        reviewRepository.deleteById(id);
    }
}
