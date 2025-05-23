package com.moabom.backend.person.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content")
public class PersonContent {
    @Id
    @Column(name = "content_id")
    private Integer contentId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "poster")
    private String poster;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "release_date")
    private String releaseDate;
} 