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
@Table(name = "ott")
public class PersonOttMaster {
    @Id
    @Column(name = "ott_id")
    private Integer ottId;
    
    @Column(name = "ott_name")
    private String ottName;
} 