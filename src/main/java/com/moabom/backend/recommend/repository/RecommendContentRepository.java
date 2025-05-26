package com.moabom.backend.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moabom.backend.recommend.model.RecommendContent;

public interface RecommendContentRepository extends JpaRepository<RecommendContent, Long> {
}
