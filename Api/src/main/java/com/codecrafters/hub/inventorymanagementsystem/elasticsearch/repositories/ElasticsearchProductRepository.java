package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
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
}
