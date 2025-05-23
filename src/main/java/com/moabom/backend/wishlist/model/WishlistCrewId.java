package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistCrewId implements Serializable {
    private Long contentId;
    private Long personId;
    
}
