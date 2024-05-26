package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class ElasticsearchProductRepository {
    private final ElasticsearchClient client;

    public IndexResponse add(IndexRequest<Product> request) throws IOException {
        return client.index(request);
    }

    public UpdateResponse<Product> update(UpdateRequest<Product, Product> request) throws IOException {
        return client.update(request, Product.class);
    }

    public DeleteResponse delete(DeleteRequest request) throws IOException {
        return client.delete(request);
    }
}
