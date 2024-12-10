package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class BaseService<T, ID> {
    private final BaseRepository<T, ID> baseRepository;

    protected BaseService(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    protected T save(T entity) {
        return baseRepository.save(entity);
    }

    protected List<T> saveAll(List<T> entities) {
        return baseRepository.saveAll(entities);
    }

    public <R> BasePaginatedResponse<R> findAll(Pageable pageable, Class<R> type) {
        var page = baseRepository.findAllBy(pageable, type);
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
        return baseRepository.findById(id, type).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(ID id) throws EntityNotFoundException {
        if (!baseRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        baseRepository.deleteById(id);
    }
}
