package com.example.beshoes.service.impl;



import com.example.beshoes.models.Order;
import com.example.beshoes.models.User;
import com.example.beshoes.models.request.AddOrderRequest;
import com.example.beshoes.models.request.UpdateOrderRequest;
import com.example.beshoes.repository.*;
import com.example.beshoes.service.CustomUserDetailService;
import com.example.beshoes.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceimpl implements OrderService {

    private final CustomUserDetailService userDetailsService;
    private final ProductRepository productRepository;
    private final OrderDeatailRepository orderDeatailRepository;
    private final OrderRepository orderRepository;
    private final CustomUserDetailService userDetailService;
    private final CartRepository cartRepository;
    private final ProductCartRepository productCartRepository;

    public OrderServiceimpl(CustomUserDetailService userDetailsService, ProductRepository productRepository, OrderDeatailRepository orderDeatailRepository, OrderRepository orderRepository, CustomUserDetailService userDetailService, CartRepository cartRepository, ProductCartRepository productCartRepository) {
        this.userDetailsService = userDetailsService;
        this.productRepository = productRepository;
        this.orderDeatailRepository = orderDeatailRepository;
        this.orderRepository = orderRepository;
        this.userDetailService = userDetailService;
        this.cartRepository = cartRepository;
        this.productCartRepository = productCartRepository;
    }

    @Override
    public Order generateOrder(AddOrderRequest request) {
        var user = new User();
        user.setUsername(userDetailService.getPrincipal().getUsername());
        user.setPassword(userDetailService.getPrincipal().getPassword());
        user.setId(userDetailService.getPrincipal().getId());
        var order = new Order();
        order.setName(request.getName());
        order.setNumber(request.getNumber());
        order.setAddress(request.getAddress());
        order.setStatus("Chờ xử lý");
        order.setOrderDate(new Date());
        order.setUser(user);
        return orderRepository.save(order);
    }




    @Override
    public Order updateStatus(UpdateOrderRequest request) {
        var optinalOrder=orderRepository.findById(request.getId());
        if(optinalOrder.isPresent()){
            var editOrder=optinalOrder.get();
            editOrder.setStatus(request.getStatus());
            return orderRepository.save(editOrder);
        }
        return null;
    }

    @Override
    public List<Order> getListOrderByOrderSuccess() {
        return orderRepository.findByStatus("Chờ xử lý");
    }
    @Override
    public List<Order> getListOrderByOrderDelivery() {
        return orderRepository.findByStatus("Đang Giao Hàng");
    }
    @Override
    public List<Order> getListOrderByOrderComplete() {
        return orderRepository.findByStatus("Đã Giao Thành Công");
    }
    @Override
    public List<Order> getListOrderByOrderCancel() {
        return orderRepository.findByStatus("Đã Hủy Đơn Hàng");
    }

    @Override
    public List<Order> findOrderByUserId() {
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());
        return orderRepository.findByUserId(user.getId());
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }
    @Override
    public List<Order> listName(String status, String name) {
            return orderRepository.search(status, name);
    }
    @Override
    public int sumMoney() {
        var sum=0;
        var listCart=orderRepository.findAll();
        for (int i=0; i<listCart.size(); i++) {
            sum+=listCart.get(i).getTotalPriceOrder();
        }
        return sum;
    }
}
