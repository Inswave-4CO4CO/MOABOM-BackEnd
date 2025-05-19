package com.moabom.backend.content.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentOtt {
    @EmbeddedId
    private ContentOttId id;

    @ManyToOne
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @MapsId("ottId")
    @JoinColumn(name = "ott_id")
    private Ott ott;

    private String url;
}
