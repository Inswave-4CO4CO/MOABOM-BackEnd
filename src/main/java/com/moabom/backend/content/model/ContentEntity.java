package com.moabom.backend.content.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "content")
public class ContentEntity {
    @Id
    @Column(name = "CONTENT_ID")
    private Integer contentId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RELEASE_DATE")
    private Date releaseDate;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "RUNNING_TIME")
    private String runningTime;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "RATING")
    private Float rating;

    @Column(name = "AGE_RATING")
    private Integer ageRating;

    @Column(name = "MADE_IN")
    private String madeIn;

    @Column(name = "POSTER")
    private String poster;

    @Column(name = "IMDB_RATING")
    private Float imdbRating;
}
