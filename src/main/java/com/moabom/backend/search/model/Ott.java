package com.moabom.backend.domain.search.model;

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

@Entity
@Table(name = "ott")
@Data
public class Ott {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OTT_ID")
    private Long ottId;

    @Column(name = "OTT_NAME", unique = true, nullable = false, length = 20)
    private String ottName;

    @ManyToMany(mappedBy = "otts")
    private Set<Content> contents = new HashSet<>();
}