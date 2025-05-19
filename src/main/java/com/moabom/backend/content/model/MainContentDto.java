package com.moabom.backend.content.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainContentDto {
    private Integer contentId;
    private String title;
    private String poster;
    private String ottName;
}
