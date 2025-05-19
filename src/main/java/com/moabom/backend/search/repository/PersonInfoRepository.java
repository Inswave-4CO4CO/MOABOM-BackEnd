package com.moabom.backend.search.repository;

import com.moabom.backend.search.model.SearchPersonInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonInfoRepository extends JpaRepository<SearchPersonInfo, Long> {
    Page<SearchPersonInfo> findDistinctBySearchCastsIsNotEmptyAndPersonNameContaining(
        String keyword, Pageable pageable);

    Page<SearchPersonInfo> findDistinctBySearchCrewsIsNotEmptyAndPersonNameContaining(
        String keyword, Pageable pageable);
}
