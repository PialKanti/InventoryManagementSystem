package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

import java.util.List;

public interface CategoryProductProjection {
    List<ProductProjection> getProducts();
}
