package com.moabom.backend.user.mapping;

import com.moabom.backend.user.model.MyWatchCountDTO;
import com.moabom.backend.user.model.MyWatchDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "MyWatchCountDTOMapping",
        classes = @ConstructorResult(
                targetClass = MyWatchCountDTO.class,
                columns = {
                        @ColumnResult(name = "COUNT", type = Integer.class),
                }
        )
)
@Entity
public class MyWatchCountResultMappingEntity {
    @Id
    private Long id;
}