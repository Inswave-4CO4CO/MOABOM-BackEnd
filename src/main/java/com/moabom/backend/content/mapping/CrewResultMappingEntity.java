package com.moabom.backend.content.mapping;

import com.moabom.backend.content.model.CrewDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "CrewDTOMapping",
        classes = @ConstructorResult(
                targetClass = CrewDTO.class,
                columns = {
                        @ColumnResult(name = "PERSON_ID", type = Integer.class),
                        @ColumnResult(name = "PERSON_NAME", type = String.class),
                        @ColumnResult(name = "IMAGE", type = String.class),
                        @ColumnResult(name = "ROLE", type = String.class)
                }
        )
)

@Entity
public class CrewResultMappingEntity {
    @Id
    private Long id;
}
