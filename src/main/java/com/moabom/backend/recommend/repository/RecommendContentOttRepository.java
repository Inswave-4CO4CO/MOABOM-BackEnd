package com.moabom.backend.recommend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moabom.backend.recommend.model.RecommendContent;
import com.moabom.backend.recommend.model.RecommendContentOtt;

public interface RecommendContentOttRepository extends JpaRepository<RecommendContentOtt, Long> {
	List<RecommendContentOtt> findByRecommendContent(RecommendContent Recommendcontent);
}