package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class AddProductRequest {

    private String categoryName;
    private String name;
    private String size;
    private int price;
    private String description;
    private int quantity;
    private String color;


}
