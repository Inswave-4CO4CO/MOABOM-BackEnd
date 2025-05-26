package com.moabom.backend.wishlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott")
public class WishlistOtt {
    @Id
    @Column(name = "OTT_ID")
    private Long ottId;
    
    @Column(name = "OTT_NAME")
    private String ottName;
}

