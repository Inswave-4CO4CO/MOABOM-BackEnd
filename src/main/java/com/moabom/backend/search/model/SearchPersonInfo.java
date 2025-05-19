// SearchPersonInfo.java
package com.moabom.backend.search.model;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "person_info")
@Data
@ToString(exclude = {"searchCasts", "searchCrews"})
@EqualsAndHashCode(exclude = {"searchCasts", "searchCrews"})
public class SearchPersonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID")
    private Long personId;

    @Column(name = "PERSON_NAME", unique = true, nullable = false, length = 20)
    private String personName;

    @Column(name = "IMAGE", length = 2000)
    private String image;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<SearchCast> searchCasts = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<SearchCrew> searchCrews = new HashSet<>();
}
