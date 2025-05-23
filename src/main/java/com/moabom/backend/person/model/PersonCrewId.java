package com.moabom.backend.person.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCrewId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer contentId;
    private Integer personId;
    private PersonCrewRole role;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCrewId crewId = (PersonCrewId) o;
        return Objects.equals(contentId, crewId.contentId) &&
               Objects.equals(personId, crewId.personId) &&
               role == crewId.role;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contentId, personId, role);
    }
} 