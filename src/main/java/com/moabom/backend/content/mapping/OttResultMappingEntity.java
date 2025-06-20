package com.moabom.backend.content.mapping;

import com.moabom.backend.content.model.OttDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "OttEntityMapping",
        classes = @ConstructorResult(
                targetClass = OttDTO.class,
                columns = {
                        @ColumnResult(name = "OTT_ID", type = Integer.class),
                        @ColumnResult(name = "OTT_NAME", type = String.class),
                        @ColumnResult(name = "URL", type = String.class),
                }
        )
)

@Entity
public class OttResultMappingEntity {
    @Id
    private Long id;
}
