package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.documents;

import com.codecrafters.hub.inventorymanagementsystem.elasticsearch.constants.Indexes;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = Indexes.INDEX_PRODUCT)
@Data
@Builder
public class Product {
    @Id
    private long id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Long)
    private long categoryId;
    @Field(type = FieldType.Float)
    private float price;
}
