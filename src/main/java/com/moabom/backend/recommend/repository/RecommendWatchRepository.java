package com.moabom.backend.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moabom.backend.recommend.model.RecommendWatch;
import com.moabom.backend.recommend.model.RecommendWatchId;

public interface RecommendWatchRepository extends JpaRepository<RecommendWatch, RecommendWatchId> {
    List<RecommendWatch> findByUserIdAndType(String userId, String type);
}
