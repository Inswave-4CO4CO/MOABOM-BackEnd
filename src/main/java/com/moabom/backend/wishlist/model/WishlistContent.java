package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content")
public class WishlistContent {
    @Id
    @Column(name = "CONTENT_ID")
    private Long contentId;

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

    @OneToMany(mappedBy = "wishlistContent")
    private List<WishlistContentOtt> wishlistContentOtts;

    @OneToMany(mappedBy = "wishlistContent")
    private List<WishlistCast> wishlistCasts;

    @OneToMany(mappedBy = "wishlistContent")
    private List<WishlistCrew> wishlistCrews;
}
