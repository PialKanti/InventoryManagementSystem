package com.codecrafters.hub.inventorymanagementsystem.dtos.request.products;

import com.codecrafters.hub.inventorymanagementsystem.dtos.request.CreateRequest;

public class ProductCreateRequest implements CreateRequest {
    private String title;
    private String description;
    private float price;
    private int quality;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
