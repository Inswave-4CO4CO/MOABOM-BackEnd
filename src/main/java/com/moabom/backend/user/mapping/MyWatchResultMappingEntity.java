package com.moabom.backend.user.mapping;

import com.moabom.backend.user.model.MyWatchDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "MyWatchDTOMapping",
        classes = @ConstructorResult(
                targetClass = MyWatchDTO.class,
                columns = {
                        @ColumnResult(name = "CONTENT_ID", type = Integer.class),
                        @ColumnResult(name = "TITLE", type = String.class),
                        @ColumnResult(name = "POSTER", type = String.class)
                }
        )
)
@Entity
public class MyWatchResultMappingEntity {
    @Id
    private Long id;
}
