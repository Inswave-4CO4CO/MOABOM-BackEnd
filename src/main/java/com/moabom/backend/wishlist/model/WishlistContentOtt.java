package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WishlistContentOttId.class)
@Table(name = "content_ott")
public class WishlistContentOtt {
    @Id
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Id
    @Column(name = "OTT_ID")
    private Long ottId;

    @Column(name = "URL")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID", insertable = false, updatable = false)
    private WishlistContent wishlistContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OTT_ID", insertable = false, updatable = false)
    private WishlistOtt wishlistOtt;
}
