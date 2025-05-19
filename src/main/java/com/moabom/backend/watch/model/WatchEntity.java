package com.moabom.backend.watch.model;

import com.moabom.backend.watch.type.WatchEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(WatchId.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "watch")
public class WatchEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "CONTENT_ID")
    private int contentId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private WatchEnum type;
}
