package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    <T> List<T> findAllBy(Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);
}
