package com.codecrafters.hub.inventorymanagementsystem.repository.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.BasePaginatedResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products.ProductSearchRequest;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.elasticsearch.ProductDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElasticsearchProductRepository {
    private final ElasticsearchClient client;
    private final ElasticsearchOperations searchOperations;

    public IndexResponse add(IndexRequest<ProductDocument> request) throws IOException {
        return client.index(request);
    }

    public UpdateResponse<ProductDocument> update(UpdateRequest<ProductDocument, ProductDocument> request) throws IOException {
        return client.update(request, ProductDocument.class);
    }

    public DeleteResponse delete(DeleteRequest request) throws IOException {
        return client.delete(request);
    }

    public BasePaginatedResponse<ProductDocument> search(ProductSearchRequest searchRequest, Pageable pageable) {
        Criteria criteria = new Criteria();

        if(searchRequest.getTitle() != null){
            criteria = criteria.and("title").contains(searchRequest.getTitle());
        }
        if (searchRequest.getCategoryId() != null) {
            criteria = criteria.and("categoryId").is(searchRequest.getCategoryId());
        }
        if (searchRequest.getMinPrice() != null && searchRequest.getMaxPrice() != null) {
            criteria = criteria.and("price").between(searchRequest.getMinPrice(), searchRequest.getMaxPrice());
        }

        Query query = new CriteriaQuery(criteria);
        query.setPageable(pageable);

        SearchHits<ProductDocument> searchHits = searchOperations.search(query, ProductDocument.class);
        List<ProductDocument> products = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

        long totalItems = searchHits.getTotalHits();
        int totalPages = (int) Math.ceil((double) totalItems / pageable.getPageSize());

        return BasePaginatedResponse.<ProductDocument>builder()
                .page(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalItems(totalItems)
                .totalPages(totalPages)
                .data(products)
                .build();
    }
}
