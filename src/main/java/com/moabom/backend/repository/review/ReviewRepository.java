package com.moabom.backend.repository.review;

import com.moabom.backend.model.review.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    @Query("SELECT r FROM ReviewEntity r WHERE r.contentId = ?1")
    List<ReviewEntity> findByContentId(int contentId);

    Page<ReviewEntity> getReviewByContentId(int contentId, Pageable pageable);

    ReviewEntity findByContentIdAndUserId(int contentId, String userId);
}