package com.moabom.backend.user.repository;

import com.moabom.backend.user.model.MyReviewDTO;
import com.moabom.backend.user.model.MyStatsDTO;
import com.moabom.backend.user.model.MyWatchDTO;
import com.moabom.backend.user.model.MyPagedResultDTO;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class MyPageRepository {
    @PersistenceContext
    private EntityManager em;

    //ott 리스트 가져오기
    public MyPagedResultDTO<MyWatchDTO> getContentByUserAndOttList(
            String userId,
            String ottList,
            String type,
            int page,
            int size
    ) {
        if (page < 1) { page = 1; }
        if (size < 1) { size = 10; }
        int offset = (page - 1) * size;
        int limit = size;

        //전체 개수
        Query countQuery = em.createNativeQuery("CALL get_content_count_by_user_and_ott_list(:userId, :ottList, :type)");
        countQuery.setParameter("userId", userId);
        countQuery.setParameter("ottList", ottList);
        countQuery.setParameter("type", type);

        long totalCount = 0;

        try {
            Object result = countQuery.getSingleResult();
            if (result instanceof Number) { totalCount = ((Number) result).longValue(); }
        } catch (NoResultException | NonUniqueResultException e) { /* ignore or log */ } catch (Exception e) { throw new RuntimeException("카운트 오류", e); }

        if (totalCount <= 0) {
            MyPagedResultDTO<MyWatchDTO> emptyResult = new MyPagedResultDTO<>();
            emptyResult.setContent(Collections.emptyList());
            emptyResult.setTotalCount(0);
            emptyResult.setTotalPages(0);
            return emptyResult;
        }

        List<MyWatchDTO> content = em.createNativeQuery(
                        "CALL get_content_by_user_and_ott_list(:userId, :ottList, :type, :offset, :limit)", "MyWatchDTOMapping")
                .setParameter("userId", userId)
                .setParameter("ottList", ottList)
                .setParameter("type", type)
                .setParameter("offset", offset)
                .setParameter("limit", limit)
                .getResultList();

        MyPagedResultDTO<MyWatchDTO> result = new MyPagedResultDTO<>();
        result.setContent(content);
        result.setTotalCount(totalCount);
        result.setCurrentPage(page);
        long totalPages = (long) Math.ceil((double) totalCount / size);
        result.setTotalPages(totalPages);

        return result;
    }


    //장르별 시청통계
    public List<MyStatsDTO> getStatsByUserId(String userId) {
        return em.createNativeQuery(
                        "CALL get_stats_by_user_id(:userId)", "MyStatsDTOMapping")
                .setParameter("userId", userId)
                .getResultList();
    }


    //리뷰 가져오기
    public MyPagedResultDTO<MyReviewDTO> getReviewByUserId(
            String userId,
            int page,
            int size
    ) {
        if (page < 1) { page = 1; }
        if (size < 1) { size = 10; }
        int offset = (page - 1) * size;
        int limit = size;

        //전체 개수
        Query countQuery = em.createNativeQuery("CALL get_count_review_by_user_id(:userId)");
        countQuery.setParameter("userId", userId);

        long totalCount = 0;

        try {
            Object result = countQuery.getSingleResult();
            if (result instanceof Number) { totalCount = ((Number) result).longValue(); }
        } catch (NoResultException | NonUniqueResultException e) { /* ignore or log */ } catch (Exception e) { throw new RuntimeException("카운트 오류", e); }

        if (totalCount <= 0) {
            MyPagedResultDTO<MyReviewDTO> emptyResult = new MyPagedResultDTO<>();
            emptyResult.setContent(Collections.emptyList());
            emptyResult.setTotalCount(0);
            emptyResult.setTotalPages(0);
            return emptyResult;
        }

        List<MyReviewDTO> content = em.createNativeQuery(
                        "CALL get_review_by_user_id(:userId, :offset, :limit)", "MyReviewDTOMapping")
                .setParameter("userId", userId)
                .setParameter("offset", offset)
                .setParameter("limit", limit)
                .getResultList();

        MyPagedResultDTO<MyReviewDTO> result = new MyPagedResultDTO<>();
        result.setContent(content);
        result.setTotalCount(totalCount);
        result.setCurrentPage(page);
        long totalPages = (long) Math.ceil((double) totalCount / size);
        result.setTotalPages(totalPages);

        return result;
    }

}