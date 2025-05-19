package com.moabom.backend.content.model;

import com.moabom.backend.content.type.CastEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CastDTO {
    private int personId;
    private String personName;
    private String image;
    private CastEnum role;

    public CastDTO(int personId, String personName, String image, String role) {
        this.personId = personId;
        this.personName = personName;
        this.image = image;

        try {
            this.role = CastEnum.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.role = null;
        }
    }
}
