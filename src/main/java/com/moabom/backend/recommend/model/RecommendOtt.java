package com.moabom.backend.recommend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ott")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendOtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OTT_ID")
    private Long ottId;

    @Column(name = "OTT_NAME")
    private String ottName;

    @OneToMany(mappedBy = "recommendOtt")
    private List<RecommendPlanDetails> recommendPlanDetails;

    @OneToMany(mappedBy = "recommendOtt")
    private List<RecommendContentOtt> recommendContentOttList;
}