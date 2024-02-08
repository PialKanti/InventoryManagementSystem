package com.codecrafters.hub.inventorymanagementsystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, Id> extends JpaRepository<T, Id> {
    <R> List<R> findAllBy(Class<R> type);
    <R> Page<R> findAllBy(Pageable pageable, Class<R> type);

    <R> Optional<R> findById(Id id, Class<R> type);
}
