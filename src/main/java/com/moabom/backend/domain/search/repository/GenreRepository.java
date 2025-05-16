package com.moabom.backend.domain.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.moabom.backend.domain.search.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

	@Query("SELECT g.genreName FROM Genre g")
	  List<String> findAllGenreNames();
}
