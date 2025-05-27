package com.moabom.backend.content.mapping;

import com.moabom.backend.content.model.CastDTO;
import com.moabom.backend.content.model.ContentReviewDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "ContentReviewDTOMapping",
        classes = @ConstructorResult(
                targetClass = ContentReviewDTO.class,
                columns = {
                        @ColumnResult(name = "REVIEW_ID", type = Integer.class),
                        @ColumnResult(name = "REVIEW_TEXT", type = String.class),
                        @ColumnResult(name = "CREATED_AT", type = LocalDateTime.class),
                        @ColumnResult(name = "RATING", type = Float.class),
                        @ColumnResult(name = "CONTENT_ID", type = Integer.class),
                        @ColumnResult(name = "USER_ID", type = String.class),
                        @ColumnResult(name = "NICKNAME", type = String.class),
                        @ColumnResult(name = "USER_IMAGE", type = String.class)
                }
        )
)

@Entity
public class ContentResultMappingEntity {
    @Id
    private Long id;
}