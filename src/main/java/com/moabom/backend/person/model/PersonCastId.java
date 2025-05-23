package com.moabom.backend.person.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCastId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer contentId;
    private Integer personId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCastId castId = (PersonCastId) o;
        return Objects.equals(contentId, castId.contentId) &&
               Objects.equals(personId, castId.personId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contentId, personId);
    }
} 