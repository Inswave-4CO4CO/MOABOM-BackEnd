package com.moabom.backend.user.mapping;

import com.moabom.backend.user.model.MyReviewDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "MyReviewDTOMapping",
        classes = @ConstructorResult(
                targetClass = MyReviewDTO.class,
                columns = {
                        @ColumnResult(name = "REVIEW_ID", type = Integer.class),
                        @ColumnResult(name = "REVIEW_TEXT", type = String.class),
                        @ColumnResult(name = "CREATED_AT", type = LocalDateTime.class),
                        @ColumnResult(name = "RATING", type = Float.class),
                        @ColumnResult(name = "CONTENT_ID", type = Integer.class),
                        @ColumnResult(name = "TITLE", type = String.class),
                }
        )
)

@Entity
public class MyReviewResultMappingEntity {
    @Id
    private Long id;
}