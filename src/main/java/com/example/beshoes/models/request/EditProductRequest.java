package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class EditProductRequest {
    private Long id;
    private String name;
    private String categoryName;
    private String size;
    private int price;
    private String description;
    private int quantity;
    private String color;
}
