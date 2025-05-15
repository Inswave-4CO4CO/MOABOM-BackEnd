package com.moabom.backend.mapping;

import com.moabom.backend.model.CastAndCrewDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "CastAndCrewEntityMapping",
        classes = @ConstructorResult(
                targetClass = CastAndCrewDTO.class,
                columns = {
                        @ColumnResult(name = "PERSON_ID", type = Integer.class),
                        @ColumnResult(name = "PERSON_NAME", type = String.class),
                        @ColumnResult(name = "IMAGE", type = String.class),
                        @ColumnResult(name = "ROLE", type = String.class)
                }
        )
)
@Entity
public class CastResultMappingEntity {
    @Id
    private Long id;
}
