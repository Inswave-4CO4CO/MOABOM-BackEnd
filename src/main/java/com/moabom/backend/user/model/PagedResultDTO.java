package com.moabom.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResultDTO<T> {
    private List<T> content;
    private long totalCount;
    private long totalPages;
    private long currentPage;
}
