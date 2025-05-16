package com.moabom.backend.domain.search.spec;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.moabom.backend.domain.search.model.Content;
import com.moabom.backend.domain.search.model.Genre;
import com.moabom.backend.domain.search.model.Ott;

import jakarta.persistence.criteria.Join;

public class ContentSpec {

    /** title LIKE %keyword% **/
    public static Specification<Content> titleContains(String keyword) {
        return (root, query, cb) -> 
            cb.like(root.get("title"), "%" + keyword + "%");
    }

    /** genres.genreName IN (:genreNames) **/
    public static Specification<Content> genreIn(Collection<String> genreNames) {
        return (root, query, cb) -> {
            // DISTINCT 보장을 위해 query.distinct(true) 설정
            query.distinct(true);
            Join<Content,Genre> genres = root.join("genres");
            return genres.get("genreName").in(genreNames);
        };
    }

    /** otts.ottName IN (:ottNames) **/
    public static Specification<Content> ottIn(Collection<String> ottNames) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Content,Ott> otts = root.join("otts");
            return otts.get("ottName").in(ottNames);
        };
    }
    
    public static Specification<Content> categoryIn(Collection<String> categories) {
        return (root, query, cb) ->
            root.get("category").in(categories);
    }
}