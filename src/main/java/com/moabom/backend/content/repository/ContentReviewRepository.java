package com.moabom.backend.content.repository;

import com.moabom.backend.content.model.CastDTO;
import com.moabom.backend.content.model.ContentReviewDTO;
import com.moabom.backend.user.model.MyPagedResultDTO;
import com.moabom.backend.user.model.MyReviewDTO;
import com.moabom.backend.user.model.MyStatsDTO;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ContentReviewRepository {
    @PersistenceContext
    private EntityManager em;

    //리뷰 가져오기
    public MyPagedResultDTO<ContentReviewDTO> getReviewByContentId(
            int contentId,
            int page,
            int size
    ) {
        if (page < 1) { page = 1; }
        if (size < 1) { size = 10; }
        int offset = (page - 1) * size;
        int limit = size;

        //전체 개수
        Query countQuery = em.createNativeQuery("CALL get_review_count_by_content_id(:contentId)");
        countQuery.setParameter("contentId", contentId);

        long totalCount = 0;

        try {
            Object result = countQuery.getSingleResult();
            if (result instanceof Number) { totalCount = ((Number) result).longValue(); }
        } catch (NoResultException | NonUniqueResultException e) { /* ignore or log */ } catch (Exception e) { throw new RuntimeException("카운트 오류", e); }

        if (totalCount <= 0) {
            MyPagedResultDTO<ContentReviewDTO> emptyResult = new MyPagedResultDTO<>();
            emptyResult.setContent(Collections.emptyList());
            emptyResult.setTotalCount(0);
            emptyResult.setTotalPages(0);
            return emptyResult;
        }

        List<ContentReviewDTO> content = em.createNativeQuery(
                        "CALL get_review_by_content_id(:contentId, :offset, :limit)", "ContentReviewDTOMapping")
                .setParameter("contentId", contentId)
                .setParameter("offset", offset)
                .setParameter("limit", limit)
                .getResultList();

        MyPagedResultDTO<ContentReviewDTO> result = new MyPagedResultDTO<>();
        result.setContent(content);
        result.setTotalCount(totalCount);
        result.setCurrentPage(page);
        long totalPages = (long) Math.ceil((double) totalCount / size);
        result.setTotalPages(totalPages);

        return result;
    }
}