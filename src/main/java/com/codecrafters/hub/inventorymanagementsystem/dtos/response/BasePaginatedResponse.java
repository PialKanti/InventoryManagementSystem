package com.codecrafters.hub.inventorymanagementsystem.dtos.response;

import java.util.List;

public class BasePaginatedResponse<T> {
    private int page;
    private int pageSize;
    private int totalItems;
    private int totalPages;
    private List<T> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public BasePaginatedResponse(int page, int pageSize, int totalItems, int totalPages, List<T> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.data = data;
    }
}
