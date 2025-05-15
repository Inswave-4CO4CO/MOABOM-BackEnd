package com.moabom.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity {
    @Id
    @Column(name = "REVIEW_ID")
    private int reviewId;

    @Column(name = "REVIEW_TEXT")
    private String reviewText;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "RATING")
    private float rating;

    @Column(name = "CONTENT_ID")
    private int contentId;

    @Column(name = "USER_ID")
    private String userId;
}
