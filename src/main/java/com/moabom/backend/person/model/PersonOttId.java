package com.moabom.backend.person.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonOttId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer contentId;
    private Integer ottId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonOttId that = (PersonOttId) o;
        return Objects.equals(contentId, that.contentId) &&
               Objects.equals(ottId, that.ottId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contentId, ottId);
    }
} 