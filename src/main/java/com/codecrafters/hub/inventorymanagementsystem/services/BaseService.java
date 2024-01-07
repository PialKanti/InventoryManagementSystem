package com.codecrafters.hub.inventorymanagementsystem.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, Id, CreateRequest, UpdateRequest> {
    private final JpaRepository<T, Id> repository;

    public BaseService(JpaRepository<T, Id> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(Id id) {
        return repository.findById(id);
    }

    public T create(CreateRequest request) {
        T entity = convertToCreateEntity(request);
        return repository.save(entity);
    }

    public T update(Id id, UpdateRequest request) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        T entity = convertToUpdateEntity(request);
        return repository.save(entity);
    }

    public void deleteById(Id id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        repository.deleteById(id);
    }

    protected abstract T convertToCreateEntity(CreateRequest request);

    protected abstract T convertToUpdateEntity(UpdateRequest request);
}
