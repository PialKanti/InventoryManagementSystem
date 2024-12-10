package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import com.codecrafters.hub.inventorymanagementsystem.model.entity.CartItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CartResponse extends EntityResponse {
    private String username;
    private List<CartItem> cartItems;
}
