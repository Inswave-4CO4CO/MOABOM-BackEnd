package com.moabom.backend.domain.search.model;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

//PersonInfo.java
@Entity
@Table(name = "person_info")
@Data
public class PersonInfo {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long personId;

 @Column(unique = true, nullable = false, length = 20)
 private String personName;

 @Column(length = 2000)
 private String image;

 @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
 private Set<Cast> casts = new HashSet<>();

 @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
 private Set<Crew> crews = new HashSet<>();

 // getters / setters…
}

