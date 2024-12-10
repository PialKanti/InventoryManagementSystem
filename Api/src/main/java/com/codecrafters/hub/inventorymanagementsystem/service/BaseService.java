package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

public abstract class BaseService<T, ID> {
    private final BaseRepository<T, ID> repository;

    protected BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public <R> BasePaginatedResponse<R> findAll(Pageable pageable, Class<R> type) {
        var page = repository.findAllBy(pageable, type);
        return BasePaginatedResponse
                .<R>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }

    public <R> R findById(ID id, Class<R> type) {
        return repository.findById(id, type).orElseThrow(EntityNotFoundException::new);
    }

    protected T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        repository.deleteById(id);
    }
}
