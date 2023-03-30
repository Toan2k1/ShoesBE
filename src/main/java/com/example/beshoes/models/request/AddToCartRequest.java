package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private long id;
    private int quantity;
}
