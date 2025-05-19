package com.moabom.backend.main.model;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingContentDto {
	private Integer contentId;
    private String title;
    private String image;
    private String ottName;
    private LocalDate releaseDate;
}

