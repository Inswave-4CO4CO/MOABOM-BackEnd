package com.moabom.backend.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moabom.backend.person.model.PersonOtt;
import com.moabom.backend.person.model.PersonOttId;

@Repository
public interface PersonOttRepository extends JpaRepository<PersonOtt, PersonOttId> {
    
    @Query("SELECT o FROM PersonOtt o WHERE o.contentId IN :contentIds")
    List<PersonOtt> findByContentIds(@Param("contentIds") List<Integer> contentIds);
} 