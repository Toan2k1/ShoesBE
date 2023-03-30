package com.example.beshoes.models.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AddOrderResponse {

    private Date orderDate;

    private String address , number, name;

    private String status;

    private long totalPriceOrder;

    private long quantityProduct;

    private List<AddOrderDetailResponse> orderdetails = new ArrayList<>();

    public AddOrderResponse(Date orderDate, String name, String address, String number, String status, long quantityProduct, long totalPriceOrder, List<AddOrderDetailResponse> list) {
        this.orderDate = orderDate;
        this.name = name;
        this.address = address;
        this.number = number;
        this.status = status;
        this.quantityProduct = quantityProduct;
        this.totalPriceOrder = totalPriceOrder;
        this.orderdetails = list;
    }
}
