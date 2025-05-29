package com.moabom.backend.search.spec;

import java.util.Collection;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import com.moabom.backend.search.model.SearchContent;
import com.moabom.backend.search.model.SearchGenre;
import com.moabom.backend.search.model.SearchOtt;

public class ContentSpec {

    /** FULLTEXT MATCH ... AGAINST 사용 */
    public static Specification<SearchContent> titleContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();  // 검색어 없으면 항상 true
            }
            // match(title) against(:keyword in boolean mode) > 0
            Expression<Double> relevance = cb.function(
                "match_against",      // 위에서 등록한 함수 이름
                Double.class,
                root.get("title"),    // 1번 인자 ?1
                cb.literal(keyword+"*")   // 2번 인자 ?2
            );
            return cb.greaterThan(relevance, cb.literal(0.0));
        };
    }

    /** genres.genreName IN :genreNames (unchanged) */
    public static Specification<SearchContent> genreIn(Collection<String> genreNames) {
        if (genreNames == null || genreNames.isEmpty()) {
            return Specification.where(null);
        }
        return (root, query, cb) -> {
            query.distinct(true);
            Join<SearchContent, SearchGenre> join = root.join("searchGenres");
            return join.get("genreName").in(genreNames);
        };
    }

    /** otts.ottName IN :ottNames (unchanged) */
    public static Specification<SearchContent> ottIn(Collection<String> ottNames) {
        if (ottNames == null || ottNames.isEmpty()) {
            return Specification.where(null);
        }
        return (root, query, cb) -> {
            query.distinct(true);
            Join<SearchContent, SearchOtt> join = root.join("searchOtts");
            return join.get("ottName").in(ottNames);
        };
    }
    
    /** category 를 LIKE 로 검색하던 부분 그대로 두거나, 필요시 MATCH AGAINST 로 대체 */
    public static Specification<SearchContent> categoryIn(Collection<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.or(
            categories.stream()
                .map(cat -> cb.like(root.get("category"), "%" + cat + "%"))
                .toArray(jakarta.persistence.criteria.Predicate[]::new)
        );
    }
}