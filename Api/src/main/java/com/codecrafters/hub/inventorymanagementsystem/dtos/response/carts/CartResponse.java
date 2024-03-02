package com.codecrafters.hub.inventorymanagementsystem.dtos.response.carts;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CartResponse extends EntityResponse {
}
