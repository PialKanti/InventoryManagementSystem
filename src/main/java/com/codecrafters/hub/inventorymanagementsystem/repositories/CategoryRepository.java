package com.codecrafters.hub.inventorymanagementsystem.repositories;

import com.codecrafters.hub.inventorymanagementsystem.entities.Category;
import com.codecrafters.hub.inventorymanagementsystem.entities.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    /*@EntityGraph(value = "Category.products", type = EntityGraph.EntityGraphType.LOAD)
    <T> T findProductsById(Long id, Class<T> type);*/
    @Query("select p.id, p.title, p.description, p.price, p.quantity from Product p where p.category.id = :categoryId")
    Page<ProductProjection> findProductsByCategoryId(long categoryId, Pageable pageable);
}
