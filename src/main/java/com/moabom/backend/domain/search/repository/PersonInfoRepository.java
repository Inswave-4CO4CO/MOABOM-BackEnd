package com.moabom.backend.domain.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moabom.backend.domain.search.model.PersonInfo;

//PersonInfoRepository.java
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Long> {
	Page<PersonInfo> findDistinctByCastsIsNotEmptyAndPersonNameContaining(
	        String keyword, Pageable pageable);
	    Page<PersonInfo> findDistinctByCrewsIsNotEmptyAndPersonNameContaining(
	        String keyword, Pageable pageable);
}