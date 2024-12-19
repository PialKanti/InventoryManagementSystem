package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.repository.BaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class BaseCrudService<T, ID> {
    private final BaseRepository<T, ID> baseRepository;
    private final ObjectMapper objectMapper;

    protected BaseCrudService(BaseRepository<T, ID> baseRepository,
                              ObjectMapper objectMapper) {
        this.baseRepository = baseRepository;
        this.objectMapper = objectMapper;
    }

    public <R> R findById(ID id, Class<R> type){
        return baseRepository.findById(id, type)
                .orElseThrow(this::entityNotFoundException);
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

    protected T save(T entity) {
        return baseRepository.save(entity);
    }

    protected List<T> saveAll(List<T> entities) {
        return baseRepository.saveAll(entities);
    }

    public void deleteById(ID id) {
        if (!baseRepository.existsById(id)) {
            throw entityNotFoundException();
        }

        baseRepository.deleteById(id);
    }

    protected <R> R mapToDto(T entity, Class<R> dtoClass) {
        return objectMapper.convertValue(entity, dtoClass);
    }

    protected abstract String getEntityNotFoundMessage();

    protected EntityNotFoundException entityNotFoundException() {
        return new EntityNotFoundException(getEntityNotFoundMessage());
    }
}
