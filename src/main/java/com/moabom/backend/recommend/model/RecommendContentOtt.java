package com.moabom.backend.recommend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "content_ott")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendContentOtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @ManyToOne
    @JoinColumn(name = "CONTENT_ID", insertable = false, updatable = false)
    private RecommendContent recommendContent;

    @ManyToOne
    @JoinColumn(name = "OTT_ID", insertable = false, updatable = false)
    private RecommendOtt recommendOtt;
}
