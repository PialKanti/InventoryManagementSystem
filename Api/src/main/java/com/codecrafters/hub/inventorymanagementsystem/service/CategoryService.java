package com.codecrafters.hub.inventorymanagementsystem.service;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryCreateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories.CategoryUpdateRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.categories.CategoryResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.Category;
import com.codecrafters.hub.inventorymanagementsystem.model.projection.ProductProjection;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import com.codecrafters.hub.inventorymanagementsystem.repository.CategoryRepository;
import com.codecrafters.hub.inventorymanagementsystem.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.codecrafters.hub.inventorymanagementsystem.constant.RedisHashKey.CATEGORY_CACHE_KEY_PREFIX;

@Service
public class CategoryService extends BaseCrudService<Category, Long> {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CacheService cacheService,
                           ObjectMapper objectMapper) {
        super(categoryRepository, objectMapper);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
    }

    public BasePaginatedResponse<ProductProjection> getProductsByCategoryId(Long id, Pageable pageable) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionConstant.CATEGORY_NOT_FOUND.getMessage());
        }

        var page = productRepository.findByCategoryId(id, pageable, ProductProjection.class);

        return BasePaginatedResponse
                .<ProductProjection>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }

    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = Category
                .builder()
                .name(request.name())
                .build();

        Category savedCategory = save(category);
        cacheService.put(CATEGORY_CACHE_KEY_PREFIX + savedCategory.getId(), savedCategory);

        return mapToDto(savedCategory, CategoryResponse.class);
    }

    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        Category category = findById(id, Category.class);
        category.setName(request.name());

        Category savedCategory = save(category);
        cacheService.put(CATEGORY_CACHE_KEY_PREFIX + savedCategory.getId(), savedCategory);

        return mapToDto(savedCategory, CategoryResponse.class);
    }

    @Override
    public <R> R findById(Long id, Class<R> type) {
        var categoryFromCache = cacheService.get(CATEGORY_CACHE_KEY_PREFIX + id, Category.class);

        if(categoryFromCache.isPresent()){
            return objectMapper.convertValue(categoryFromCache.get(), type);
        }

        var categoryFromDb = super.findById(id, Category.class);
        cacheService.put(CATEGORY_CACHE_KEY_PREFIX + id, categoryFromDb);

        return objectMapper.convertValue(categoryFromDb, type);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
        cacheService.remove(CATEGORY_CACHE_KEY_PREFIX + id);
    }

    @Override
    protected String getEntityNotFoundMessage() {
        return ExceptionConstant.CATEGORY_NOT_FOUND.getMessage();
    }
}
