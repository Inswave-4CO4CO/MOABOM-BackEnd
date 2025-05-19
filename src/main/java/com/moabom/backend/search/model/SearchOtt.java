// SearchOtt.java
package com.moabom.backend.search.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ott")
@Data
@ToString(exclude = "searchContents")
@EqualsAndHashCode(exclude = "searchContents")
public class SearchOtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OTT_ID")
    private Long ottId;

    @Column(name = "OTT_NAME", unique = true, nullable = false, length = 20)
    private String ottName;

    @ManyToMany(mappedBy = "searchOtts")
    private Set<SearchContent> searchContents = new HashSet<>();
}