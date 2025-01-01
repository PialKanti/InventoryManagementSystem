package com.codecrafters.hub.inventorymanagementsystem.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RabbitQueueName {
    public static final String PRODUCT_CREATE_QUEUE = "product.create";
    public static final String PRODUCT_UPDATE_QUEUE = "product.update";
    public static final String PRODUCT_DELETE_QUEUE = "product.delete";
}
