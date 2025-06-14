package com.moabom.backend.recommend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.moabom.backend.recommend.model.RecommendOtt;

public interface RecommendOttRepository extends JpaRepository<RecommendOtt, Long> {
	Optional<RecommendOtt> findByOttName(String ottName);
}
