package com.moabom.backend.domain.search.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.moabom.backend.domain.search.model.Content;

public interface ContentRepository extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {

	@Override
	@EntityGraph(attributePaths = { "genres", "otts" })
	Page<Content> findAll(Specification<Content> spec, Pageable pageable);
	
	@Query("SELECT DISTINCT c.category FROM Content c")
	List<String> findDistinctCategories();
}