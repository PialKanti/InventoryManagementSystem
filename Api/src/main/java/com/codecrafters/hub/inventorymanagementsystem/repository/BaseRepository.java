package com.codecrafters.hub.inventorymanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, Id> extends JpaRepository<T, Id> {
    <R> Page<R> findAll(Pageable pageable, Class<R> type);
    <R> Optional<R> findById(Id id, Class<R> type);
}
