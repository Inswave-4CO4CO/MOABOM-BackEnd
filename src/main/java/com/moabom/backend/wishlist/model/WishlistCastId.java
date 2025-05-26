package com.moabom.backend.wishlist.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistCastId implements Serializable {
    private Long contentId;
    private Long personId;
    
}
