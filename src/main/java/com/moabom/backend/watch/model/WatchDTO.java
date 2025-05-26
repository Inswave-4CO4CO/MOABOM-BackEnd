package com.moabom.backend.watch.model;

import com.moabom.backend.content.model.GenreDTO;
import com.moabom.backend.watch.type.WatchEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WatchDTO {
    private String userId;
    private int contentId;
    private WatchEnum type;
    private List<GenreDTO> genre;
}
