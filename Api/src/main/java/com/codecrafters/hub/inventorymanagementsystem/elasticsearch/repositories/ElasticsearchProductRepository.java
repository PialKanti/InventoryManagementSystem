package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.dtos.request.ProductSearchRequest;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product;
import lombok.RequiredArgsConstructor;
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

    public IndexResponse add(IndexRequest<Product> request) throws IOException {
        return client.index(request);
    }

    public UpdateResponse<Product> update(UpdateRequest<Product, Product> request) throws IOException {
        return client.update(request, Product.class);
    }

    public DeleteResponse delete(DeleteRequest request) throws IOException {
        return client.delete(request);
    }

    public List<Product> search(ProductSearchRequest searchRequest) {
        Criteria criteria = new Criteria("title").contains(searchRequest.getTitle()).and("price")
                .between(searchRequest.getMinPrice(), searchRequest.getMaxPrice());
        if (searchRequest.getCategoryId() != null) {
            criteria = criteria.and("categoryId").is(searchRequest.getCategoryId());
        }

        Query query = new CriteriaQuery(criteria);
        SearchHits<Product> searchHits = searchOperations.search(query, Product.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }
}
