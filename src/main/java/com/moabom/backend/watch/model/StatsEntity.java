package com.moabom.backend.watch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "stats")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATS_ID")
    private Integer statsId;

    @Column(name = "COUNT")
    private Integer count;

    @Column(name = "GENRE_ID")
    private Integer genreId;

    @Column(name = "USER_ID")
    private String userId;
}
