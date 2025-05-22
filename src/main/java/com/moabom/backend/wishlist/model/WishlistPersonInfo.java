package com.moabom.backend.wishlist.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_info")
public class WishlistPersonInfo {
    @Id
    @Column(name = "PERSON_ID")
    private Long personId;
    
    @Column(name = "PERSON_NAME")
    private String personName;
    
    @Column(name = "IMAGE")
    private String image;
}
