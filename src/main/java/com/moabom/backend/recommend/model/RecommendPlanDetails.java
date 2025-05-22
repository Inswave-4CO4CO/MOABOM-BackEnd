package com.moabom.backend.recommend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPlanDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAN_ID")
    private Long planId;

    @Column(name = "PLAN_NAME")
    private String planName;
    
    @Column(name = "PRICE")
    private int price;

    @ManyToOne
    @JoinColumn(name = "OTT_ID")
    private RecommendOtt recommendOtt;
}
