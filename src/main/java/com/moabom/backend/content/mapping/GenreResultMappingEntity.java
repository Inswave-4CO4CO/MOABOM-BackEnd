package com.moabom.backend.content.mapping;

import com.moabom.backend.content.model.GenreDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "GenreEntityMapping",
        classes = @ConstructorResult(
                targetClass = GenreDTO.class,
                columns = {
                        @ColumnResult(name = "GENRE_NAME", type = String.class),
                        @ColumnResult(name = "GENRE_ID", type = Integer.class),
                }
        )
)

@Entity
public class GenreResultMappingEntity {
    @Id
    private Long id;
}