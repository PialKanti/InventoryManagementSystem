package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.elasticsearch")
@Getter
@Setter
public class EsProperties {
    private String host;
    private Integer port;
}
