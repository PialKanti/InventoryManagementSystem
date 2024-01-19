package com.codecrafters.hub.inventorymanagementsystem.services;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.repositories.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, Id, CreateRequest, UpdateRequest, EntityResponse> {
    private final BaseRepository<T, Id> repository;

    public BaseService(BaseRepository<T, Id> repository) {
        this.repository = repository;
    }

    public <R> List<R> findAll(Class<R> type) {
        return repository.findAllBy(type);
    }

    public <R> BasePaginatedResponse<R> findAll(Pageable pageable, Class<R> type) {
        var page = repository.findAllBy(pageable, type);
        return BasePaginatedResponse
                .<R>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }

    public <R> Optional<R> findById(Id id, Class<R> type) {
        return repository.findById(id, type);
    }

    public EntityResponse create(CreateRequest request) {
        T entity = convertToCreateEntity(request);
        return convertToEntityResponse(repository.save(entity));
    }

    public EntityResponse update(Id id, UpdateRequest request) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        T entity = convertToUpdateEntity(request);
        return convertToEntityResponse(repository.save(entity));
    }

    public void deleteById(Id id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        repository.deleteById(id);
    }

    protected abstract T convertToCreateEntity(CreateRequest request);

    protected abstract T convertToUpdateEntity(UpdateRequest request);

    protected abstract EntityResponse convertToEntityResponse(T entity);
}
