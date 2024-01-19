package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    <T> List<T> findAllBy(Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);

    <T> T findProductsById(Long id, Class<T> type);
}
