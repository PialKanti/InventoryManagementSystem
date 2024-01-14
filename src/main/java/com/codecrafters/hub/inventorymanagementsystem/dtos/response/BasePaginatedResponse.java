package com.codecrafters.hub.inventorymanagementsystem.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasePaginatedResponse<T> {
    private int page;
    private int pageSize;
    private int totalItems;
    private int totalPages;
    private List<T> data;
}
