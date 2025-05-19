// SearchGenre.java
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
@Table(name = "genre")
@Data
@ToString(exclude = "searchContents")
@EqualsAndHashCode(exclude = "searchContents")
public class SearchGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_ID")
    private Long genreId;

    @Column(name = "GENRE_NAME", unique = true, nullable = false, length = 20)
    private String genreName;

    @ManyToMany(mappedBy = "searchGenres")
    private Set<SearchContent> searchContents = new HashSet<>();
}