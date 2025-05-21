// SearchGenreRepository.java
package com.moabom.backend.search.repository;

import com.moabom.backend.search.model.SearchGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SearchGenreRepository extends JpaRepository<SearchGenre, Long> {
    /**
     * 콘텐츠에 사용된 genre 이름을 DISTINCT로 조회합니다.
     */
    @Query("SELECT DISTINCT g.genreName FROM SearchGenre g")
    List<String> findAllGenreNames();
}