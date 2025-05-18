package com.moabom.backend.content.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OttDTO {
    private int ottId;
    private String ottName;
    private String url;
}
