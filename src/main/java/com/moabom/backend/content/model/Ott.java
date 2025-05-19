package com.moabom.backend.content.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott")
public class Ott {
    @Id
    private Integer ottId;
    private String ottName;

    @OneToMany(mappedBy = "ott")
    private List<ContentOtt> contentOtts;
}
