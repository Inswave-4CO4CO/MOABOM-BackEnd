// SearchCast.java
package com.moabom.backend.search.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "cast")
@Data
@ToString(exclude = "searchContent")
@EqualsAndHashCode(exclude = "searchContent")
public class SearchCast {
    @EmbeddedId
    private SearchCompositeKey id;

    @ManyToOne
    @MapsId("contentId")
    @JoinColumn(name = "CONTENT_ID")
    private SearchContent searchContent;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "PERSON_ID")
    private SearchPersonInfo person;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('MAIN','SUB')")
    private SearchRole searchRole;
}