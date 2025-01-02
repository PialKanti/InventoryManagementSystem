package com.codecrafters.hub.inventorymanagementsystem.config.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@RequiredArgsConstructor
public class EsConfig extends ElasticsearchConfiguration {
    private final EsProperties esProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(esProperties.getHost() + ":" + esProperties.getPort())
                .build();
    }
}
