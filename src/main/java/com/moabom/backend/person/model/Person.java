package com.moabom.backend.person.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID")
    private Integer personId;

    @Column(name = "PERSON_NAME", nullable = false, unique = true, length = 20)
    private String personName;

    @Column(name = "IMAGE", length = 2000)
    private String image;
} 