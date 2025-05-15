package com.moabom.backend.mapping;

import com.moabom.backend.model.GenreDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "GenreEntityMapping",
        classes = @ConstructorResult(
                targetClass = GenreDTO.class,
                columns = {
                        @ColumnResult(name = "GENRE_NAME", type = String.class),
                }
        )
)

@Entity
public class GenreResultMappingEntity {
    @Id
    private Long id;
}