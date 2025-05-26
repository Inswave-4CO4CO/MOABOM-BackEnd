package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WishlistCrewId.class)
@Table(name = "crew")
public class WishlistCrew {
    @Id
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Id
    @Column(name = "PERSON_ID")
    private Long personId;

    @Column(name = "ROLE")
    private String role; // DIR, WRI

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID", insertable = false, updatable = false)
    private WishlistContent wishlistContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", insertable = false, updatable = false)
    private WishlistPersonInfo person;
}
