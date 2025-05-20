package com.moabom.backend.person.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crew")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PersonCrewId.class)
public class PersonCrew {
    
    @Id
    @Column(name = "CONTENT_ID")
    private Integer contentId;
    
    @Id
    @Column(name = "PERSON_ID", insertable = false, updatable = false)
    private Integer personId;
    
    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;
    
    @Id
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private PersonCrewRole role;
} 