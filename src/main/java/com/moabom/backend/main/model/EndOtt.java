package com.moabom.backend.main.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndOtt {
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

    private LocalDate endDate;
}
