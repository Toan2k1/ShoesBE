package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class AddOrderRequest {

    private String address , number, name;

    private int quantityProduct;
}
