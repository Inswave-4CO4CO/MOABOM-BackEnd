package com.moabom.backend.main.repository;

import org.springframework.data.jpa.repository.*;

import com.moabom.backend.main.model.*;

import java.time.LocalDate;
import java.util.List;

public interface EndOttRepository extends JpaRepository<EndOtt, ContentOttId> {
//    @Query("SELECT eo FROM EndOtt eo ORDER BY eo.endDate LIMIT 30")
//    List<EndOtt> findTop30ByEndDate();
    
    // 종료일 기준 빠른 순으로 30개
	List<EndOtt> findTop50ByEndDateAfterOrderByEndDateAsc(LocalDate date);
}