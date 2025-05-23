package com.moabom.backend.wishlist.model;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDto {
	private Long contentId;
    private String poster;
    private String madeIn;
    private String title;
    private LocalDate releaseDate;
    private String runningTime;
    private String castName;
    private String crewName;
    private String ottName;
}
