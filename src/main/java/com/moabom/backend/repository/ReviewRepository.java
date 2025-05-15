package com.moabom.backend.repository;

import com.moabom.backend.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    @Query("SELECT r FROM ReviewEntity r WHERE r.contentId = ?1")
    List<ReviewEntity> findByContentId(int contentId);
}