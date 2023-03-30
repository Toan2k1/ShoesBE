package com.example.beshoes.models.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private long id;

    private Date orderDate;

    private String address , number, name;

    private String status;

    private long totalPriceOrder;

    private long quantityProduct;

    private List<AddOrderDetailResponse> orderdetails = new ArrayList<>();
}
