package com.moabom.backend.main.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OTT_ID")
    private Long ottId;

    @Column(name = "OTT_NAME")
    private String ottName;

    @OneToMany(mappedBy = "ott")
    private List<ContentOtt> contentOtts;
}
