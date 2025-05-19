package com.moabom.backend.content.repository;

import org.springframework.data.jpa.repository.*;

import com.moabom.backend.content.model.Content;

import java.time.LocalDate;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Integer> {
	

//    @Query("SELECT c FROM Content c ORDER BY c.releaseDate DESC LIMIT 30")
//    List<Content> findTop30ByReleaseDate();
//
//    @Query("SELECT c FROM Content c ORDER BY c.rating DESC LIMIT 30")
//    List<Content> findTop30ByRating();
	// 최신순 30개
	List<Content> findTop50ByReleaseDateLessThanEqualOrderByReleaseDateDesc(LocalDate now);
	

    // 평점순 30개
    List<Content> findTop30ByOrderByRatingDesc();
}