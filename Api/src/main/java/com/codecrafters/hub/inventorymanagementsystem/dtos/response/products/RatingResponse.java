package com.codecrafters.hub.inventorymanagementsystem.dtos.response.products;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.users.UserResponse;
import lombok.Builder;

@Builder
public record RatingResponse(int ratings, String comment, UserResponse user) {

}
