package com.moabom.backend.person.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cast")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PersonCastId.class)
public class PersonCast {
    
    @Id
    @Column(name = "CONTENT_ID")
    private Integer contentId;
    
    @Id
    @Column(name = "PERSON_ID", insertable = false, updatable = false)
    private Integer personId;
    
    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;
    
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private PersonCastRole role;
} 