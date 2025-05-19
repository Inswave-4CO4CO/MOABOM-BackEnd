package com.moabom.backend.content.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContentOttId implements Serializable {
    private Integer contentId;
    private Integer ottId;
}
