// SearchContent.java
package com.moabom.backend.search.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "content")
@Data
@ToString(exclude = {"searchCasts", "searchCrews", "searchGenres", "searchOtts"})
@EqualsAndHashCode(exclude = {"searchCasts", "searchCrews", "searchGenres", "searchOtts"})
public class SearchContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @Column(name = "category")
    private String category;

    @Column(name = "running_time")
    private String runningTime;

    @Column(name = "image")
    private String image;

    @Column(name = "RATING")
    private Float rating;

    @Column(name = "AGE_RATING")
    private Integer ageRating;

    @Column(name = "made_in")
    private String madeIn;

    @Column(name = "poster")
    private String poster;

    @Column(name = "IMDB_RATING")
    private Float imdbRating;

    @OneToMany(mappedBy = "searchContent", cascade = CascadeType.ALL)
    private Set<SearchCast> searchCasts = new HashSet<>();

    @OneToMany(mappedBy = "searchContent", cascade = CascadeType.ALL)
    private Set<SearchCrew> searchCrews = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
      name = "content_genre",
      joinColumns = @JoinColumn(name = "CONTENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )
    private Set<SearchGenre> searchGenres = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
      name = "content_ott",
      joinColumns = @JoinColumn(name = "CONTENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "OTT_ID")
    )
    private Set<SearchOtt> searchOtts = new HashSet<>();
}