package com.example.beshoes.service;


import com.example.beshoes.models.Order;
import com.example.beshoes.models.request.AddOrderRequest;
import com.example.beshoes.models.request.UpdateOrderRequest;

import java.util.Arrays;
import java.util.List;

public interface OrderService {

    Order generateOrder(AddOrderRequest request);



    Order updateStatus(UpdateOrderRequest request);

    List<Order> getListOrderByOrderSuccess();

    List<Order> getListOrderByOrderDelivery();

    List<Order> getListOrderByOrderComplete();


    List<Order> getListOrderByOrderCancel();

    List<Order> findOrderByUserId();

    List<Order> findAllOrder();



    List<Order> listName(String status, String name);
    int sumMoney();
}
