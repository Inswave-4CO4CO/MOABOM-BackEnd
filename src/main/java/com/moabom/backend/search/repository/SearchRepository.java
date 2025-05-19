// SearchRepository.java
package com.moabom.backend.search.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.moabom.backend.search.model.SearchContent;

public interface SearchRepository extends JpaRepository<SearchContent, Long>, JpaSpecificationExecutor<SearchContent> {

    @Override
    @EntityGraph(attributePaths = { "searchGenres", "searchOtts" })
    Page<SearchContent> findAll(Specification<SearchContent> spec, Pageable pageable);

    @Query("SELECT DISTINCT c.category FROM SearchContent c")
    List<String> findDistinctCategories();
}
