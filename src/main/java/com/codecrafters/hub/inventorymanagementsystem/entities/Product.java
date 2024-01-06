package com.codecrafters.hub.inventorymanagementsystem.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product extends AuditorEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 200)
    private String title;
    @Column(nullable = true, length = 1000)
    private String description;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private int quality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Product(String title, String description, float price, int quality) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quality = quality;
    }

    public Product() {

    }
}
