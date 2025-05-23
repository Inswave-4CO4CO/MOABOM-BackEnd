package com.moabom.backend.person.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "content_ott")
@IdClass(PersonOttId.class)
public class PersonOtt {
    @Id
    @Column(name = "content_id")
    private Integer contentId;
    
    @Id
    @Column(name = "ott_id")
    private Integer ottId;
    
    @Column(name = "url")
    private String url;
} 