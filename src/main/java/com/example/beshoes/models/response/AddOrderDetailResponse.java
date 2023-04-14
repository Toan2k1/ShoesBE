package com.example.beshoes.models.response;

import lombok.Data;

@Data
public class AddOrderDetailResponse {

    private int quantity;

    private long totalPrice;

    private AddProductResponse product;
}
