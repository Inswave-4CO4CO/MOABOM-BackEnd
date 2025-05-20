package com.moabom.backend.user.mapping;

import com.moabom.backend.user.model.MyStatsDTO;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "MyStatsDTOMapping",
        classes = @ConstructorResult(
                targetClass = MyStatsDTO.class,
                columns = {
                        @ColumnResult(name = "GENRE_NAME", type = String.class),
                        @ColumnResult(name = "COUNT", type = Integer.class),
                }
        )
)

@Entity
public class MyStatsResultMappingEntity {
    @Id
    private Long id;
}