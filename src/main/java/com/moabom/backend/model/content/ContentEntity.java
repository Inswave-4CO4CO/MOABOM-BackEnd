package com.moabom.backend.model.content;

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
    private int contentId;

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
    private float rating;

    @Column(name = "AGE_RATING")
    private int ageRating;

    @Column(name = "MADE_IN")
    private String madeIn;

    @Column(name = "POSTER")
    private String poster;

    @Column(name = "IMDB_RATING")
    private float imdbRating;
}
