package com.moabom.backend.wishlist.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistContentOttId implements Serializable {
    private Long contentId;
    private Long ottId;
}
