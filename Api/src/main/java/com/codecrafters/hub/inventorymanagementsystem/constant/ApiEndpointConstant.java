package com.codecrafters.hub.inventorymanagementsystem.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiEndpointConstant {
    private static final String API_VERSION = "/api/v1";

    /* User Endpoints */
    public static final String USER_ENDPOINT = API_VERSION + "/users";

    /* Auth Endpoints */
    public static final String AUTH_ENDPOINT = API_VERSION + "/auth";

    /* Category Endpoints */
    public static final String CATEGORY_ENDPOINT = API_VERSION + "/categories";

    /* Product Endpoints */
    public static final String PRODUCT_ENDPOINT = API_VERSION + "/products";

    /* Cart Endpoints */
    public static final String CART_ENDPOINT = API_VERSION + "/carts/mine";
}
