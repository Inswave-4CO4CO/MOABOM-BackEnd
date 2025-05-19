package com.moabom.backend.content.mapping;

import com.moabom.backend.content.model.CastDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "CastDTOMapping",
        classes = @ConstructorResult(
                targetClass = CastDTO.class,
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
