package com.moabom.backend.model.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity {
    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @Column(name = "REVIEW_TEXT")
    private String reviewText;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "RATING")
    private float rating;

    @Column(name = "CONTENT_ID")
    private int contentId;

    @Column(name = "USER_ID")
    private String userId;
}
