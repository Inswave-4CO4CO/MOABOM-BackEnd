package com.moabom.backend.model.content;

import com.moabom.backend.type.CrewEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrewDTO {
    private int personId;
    private String personName;
    private String image;
    private CrewEnum role;

    public CrewDTO(int personId, String personName, String image, String role) {
        this.personId = personId;
        this.personName = personName;
        this.image = image;

        try {
            this.role = CrewEnum.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.role = null;
        }
    }
}
