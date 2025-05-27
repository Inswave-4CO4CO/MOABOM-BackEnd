package com.moabom.backend.search.repository;

import com.moabom.backend.search.model.SearchPersonInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonInfoRepository extends JpaRepository<SearchPersonInfo, Long> {
    Page<SearchPersonInfo> findDistinctBySearchCastsIsNotEmptyAndPersonNameContaining(
        String keyword, Pageable pageable);

    Page<SearchPersonInfo> findDistinctBySearchCrewsIsNotEmptyAndPersonNameContaining(
        String keyword, Pageable pageable);

    @Query("SELECT DISTINCT p FROM SearchPersonInfo p JOIN p.searchCasts sc WHERE p.personName LIKE %:keyword% AND sc.searchRole IN (com.moabom.backend.search.model.SearchRole.MAIN, com.moabom.backend.search.model.SearchRole.SUB)")
    Page<SearchPersonInfo> findActorsByName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT p FROM SearchPersonInfo p JOIN p.searchCrews scw WHERE p.personName LIKE %:keyword% AND scw.searchRole IN (com.moabom.backend.search.model.SearchRole.DIR, com.moabom.backend.search.model.SearchRole.WRI)")
    Page<SearchPersonInfo> findDirectorsAndWritersByName(@Param("keyword") String keyword, Pageable pageable);
}
