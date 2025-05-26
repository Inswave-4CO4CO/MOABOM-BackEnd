package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WishlistWatchId.class)
@Table(name = "watch")
public class WishlistWatch {
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
    private WishlistContent wishlistContent;
}