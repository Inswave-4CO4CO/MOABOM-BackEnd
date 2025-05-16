package com.moabom.backend.domain.search.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CompositeKey implements Serializable {
    private Long contentId;
    private Long personId;
    // equals, hashCode…
}