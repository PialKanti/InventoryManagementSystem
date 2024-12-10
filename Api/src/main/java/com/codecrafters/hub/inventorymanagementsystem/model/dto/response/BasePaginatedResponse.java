package com.codecrafters.hub.inventorymanagementsystem.model.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasePaginatedResponse<T> {
    private int page;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private List<T> data;
}
