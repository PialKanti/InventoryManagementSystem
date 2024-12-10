package com.codecrafters.hub.inventorymanagementsystem.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JmsDestination {
    public static final String ELASTICSEARCH_PRODUCT_CREATE = "es.product.create";
    public static final String ELASTICSEARCH_PRODUCT_UPDATE = "es.product.update";
    public static final String ELASTICSEARCH_PRODUCT_DELETE = "es.product.delete";
}
