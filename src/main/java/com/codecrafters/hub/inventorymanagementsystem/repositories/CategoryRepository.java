package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    @EntityGraph(value = "Category.products", type = EntityGraph.EntityGraphType.LOAD)
    <T> T findProductsById(Long id, Class<T> type);
}
