package com.moabom.backend.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moabom.backend.person.model.PersonOttMaster;

@Repository
public interface PersonOttMasterRepository extends JpaRepository<PersonOttMaster, Integer> {
    
    @Query("SELECT o FROM PersonOttMaster o WHERE o.ottId IN :ottIds")
    List<PersonOttMaster> findByOttIds(@Param("ottIds") List<Integer> ottIds);
} 