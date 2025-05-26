package com.moabom.backend.recommend.model;

import com.moabom.backend.wishlist.model.WishlistWatchId;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RecommendWatchId.class)
@Table(name = "watch")
public class RecommendWatch {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Column(name = "TYPE")
    private String type; // WANT, ING, ED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID", insertable = false, updatable = false)
    private RecommendContent recommendContent;
}