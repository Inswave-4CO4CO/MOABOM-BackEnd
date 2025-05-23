package com.moabom.backend.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moabom.backend.person.model.Person;
import com.moabom.backend.person.model.PersonCrew;
import com.moabom.backend.person.model.PersonCrewId;

public interface PersonCrewRepository extends JpaRepository<PersonCrew, PersonCrewId> {
    
    List<PersonCrew> findByPerson(Person person);
    
    @Query("SELECT c FROM PersonCrew c JOIN FETCH c.person WHERE c.contentId = :contentId")
    List<PersonCrew> findByContentId(@Param("contentId") Integer contentId);
} 