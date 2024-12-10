package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.users.UserResponse;
import lombok.Builder;

@Builder
public record RatingResponse(int ratings, String comment, UserResponse user) {

}
