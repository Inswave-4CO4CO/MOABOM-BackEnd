package com.moabom.backend.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moabom.backend.person.model.PersonContent;

@Repository
public interface PersonContentRepository extends JpaRepository<PersonContent, Integer> {
    
    @Query("SELECT c FROM PersonContent c WHERE c.contentId IN :contentIds")
    List<PersonContent> findByContentIds(@Param("contentIds") List<Integer> contentIds);
} 