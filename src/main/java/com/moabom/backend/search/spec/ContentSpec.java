package com.moabom.backend.search.spec;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.moabom.backend.search.model.SearchContent;
import com.moabom.backend.search.model.SearchGenre;
import com.moabom.backend.search.model.SearchOtt;

import jakarta.persistence.criteria.Join;

public class ContentSpec {

    /** title LIKE %keyword% **/
    public static Specification<SearchContent> titleContains(String keyword) {
        return (root, query, cb) -> 
            cb.like(root.get("title"), "%" + keyword + "%");
    }

    /** genres.genreName IN (:genreNames) **/
    public static Specification<SearchContent> genreIn(Collection<String> genreNames) {
        return (root, query, cb) -> {
            query.distinct(true);  // 중복 제거
            Join<SearchContent,SearchGenre> join = root.join("searchGenres");
            return join.get("genreName").in(genreNames);
        };
    }

    /** otts.ottName IN (:ottNames) **/
    public static Specification<SearchContent> ottIn(Collection<String> ottNames) {
        return (root, query, cb) -> {
            query.distinct(true);  // 중복 제거
            Join<SearchContent,SearchOtt> join = root.join("searchOtts");
            return join.get("ottName").in(ottNames);
        };
    }
    
    public static Specification<SearchContent> categoryIn(Collection<String> categories) {
        return (root, query, cb) ->
            root.get("category").in(categories);
    }
}