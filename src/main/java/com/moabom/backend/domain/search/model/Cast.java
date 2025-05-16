package com.moabom.backend.domain.search.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//Cast.java
@Entity
@Table(name = "cast")
@Data
@ToString(exclude = "content")
@EqualsAndHashCode(exclude = "content")
public class Cast {
 @EmbeddedId
 private CompositeKey id;

 @ManyToOne @MapsId("contentId")
 @JoinColumn(name = "CONTENT_ID")
 private Content content;

 @ManyToOne @MapsId("personId")
 @JoinColumn(name = "PERSON_ID")
 private PersonInfo person;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false, columnDefinition = "enum('MAIN','SUB')")
 private Role role;

 // getters / setters…
}
