package com.example.beshoes.models.response;

import lombok.Data;

@Data
public class AddProductResponse {
private long id;
    private String categoryName;
    private String name;
    private String size;
    private int price;
    private String description;
    private int quantity;
    private String color;

    private String image;


}
