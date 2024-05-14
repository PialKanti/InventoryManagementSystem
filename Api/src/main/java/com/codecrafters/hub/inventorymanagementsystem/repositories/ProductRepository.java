package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    @EntityGraph(value = "productEntityGraph")
    <R> Page<R> findAllBy(Pageable pageable, Class<R> type);
    @EntityGraph(value = "productEntityGraph")
    <R> Optional<R> findById(Long id, Class<R> type);
    <T> Page<T> findByCategoryId(Long id, Pageable pageable, Class<T> type);
}
