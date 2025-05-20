package com.moabom.backend.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moabom.backend.person.model.Person;
import com.moabom.backend.person.model.PersonCast;
import com.moabom.backend.person.model.PersonCastId;

public interface PersonCastRepository extends JpaRepository<PersonCast, PersonCastId> {
    
    List<PersonCast> findByPerson(Person person);
    
    @Query("SELECT c FROM PersonCast c JOIN FETCH c.person WHERE c.contentId = :contentId")
    List<PersonCast> findByContentId(@Param("contentId") Integer contentId);
} 