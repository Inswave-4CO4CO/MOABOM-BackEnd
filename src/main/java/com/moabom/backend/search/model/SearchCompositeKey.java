// SearchCompositeKey.java
package com.moabom.backend.search.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class SearchCompositeKey implements Serializable {
    private Long contentId;
    private Long personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCompositeKey that = (SearchCompositeKey) o;
        return contentId.equals(that.contentId) && personId.equals(that.personId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(contentId, personId);
    }
}