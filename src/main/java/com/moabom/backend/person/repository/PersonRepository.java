package com.moabom.backend.person.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moabom.backend.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    
    Optional<Person> findByPersonId(Integer personId);
    
    Page<Person> findByPersonNameContaining(String name, Pageable pageable);
    
    @Query("SELECT p FROM Person p WHERE LOWER(p.personName) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.personName")
    List<Person> searchByNameKeyword(@Param("keyword") String keyword);
} 