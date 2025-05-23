package com.moabom.backend.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moabom.backend.recommend.model.RecommendPlanDetails;

public interface RecommendPlanDetailsRepository extends JpaRepository<RecommendPlanDetails, Long> {
    List<RecommendPlanDetails> findByRecommendOtt_OttId(Long ottId);
}
