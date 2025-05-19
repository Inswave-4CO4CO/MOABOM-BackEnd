package com.moabom.backend.main.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    private String title;
    private String description;
    private LocalDate releaseDate;
    private String category;
    private String runningTime;
    private String image;
    private Double rating;
    private String ageRating;

    @Column(name = "made_in")
    private String madein;

    private String poster;
    private Double imdbRating;

    @OneToMany(mappedBy = "content")
    private List<ContentOtt> contentOtts;
}