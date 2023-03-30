package com.example.beshoes.models.response;

import lombok.Data;

@Data
public class ProductCartDto {

    private long id;

    private long quantity;

    private long totalprice;

    private AddProductResponse product;
}
