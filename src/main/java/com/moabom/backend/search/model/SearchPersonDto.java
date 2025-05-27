// SearchPersonDto.java
package com.moabom.backend.search.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonDto {
    private Long personId;
    private String personName;
    private String image;
    private List<String> roles;
}