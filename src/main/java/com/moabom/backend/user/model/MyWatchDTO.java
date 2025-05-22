package com.moabom.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWatchDTO {
    private Integer contentId;
    private String title;
    private String poster;
}
