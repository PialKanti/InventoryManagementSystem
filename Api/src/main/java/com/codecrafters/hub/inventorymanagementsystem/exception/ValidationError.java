package com.codecrafters.hub.inventorymanagementsystem.exception;

import lombok.Builder;

@Builder
public record ValidationError(String field, String message) {
}