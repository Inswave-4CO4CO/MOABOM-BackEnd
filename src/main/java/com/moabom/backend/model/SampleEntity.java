package com.moabom.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SampleEntity {

    @Id
    private Long id;
    private String name;

}
