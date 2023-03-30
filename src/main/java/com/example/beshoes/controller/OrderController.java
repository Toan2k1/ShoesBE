package com.example.beshoes.controller;

import com.example.beshoes.models.Order;
import com.example.beshoes.models.OrderDetail;
import com.example.beshoes.models.Product;
import com.example.beshoes.models.request.AddOrderRequest;
import com.example.beshoes.models.request.UpdateOrderRequest;
import com.example.beshoes.models.response.AddOrderDetailResponse;
import com.example.beshoes.models.response.AddOrderResponse;
import com.example.beshoes.models.response.OrderDto;
import com.example.beshoes.repository.CartRepository;
import com.example.beshoes.repository.OrderDeatailRepository;
import com.example.beshoes.repository.OrderRepository;
import com.example.beshoes.repository.ProductRepository;
import com.example.beshoes.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    private final OrderDeatailRepository orderDeatailRepository;

    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ProductRepository productRepository, OrderDeatailRepository orderDeatailRepository, CartRepository cartRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.orderDeatailRepository = orderDeatailRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<AddOrderResponse> addOrder(@RequestBody AddOrderRequest orderRequest,
                                                     @PathVariable long productId){
        var product = productRepository.findById(productId);
        var order = orderService.generateOrder(orderRequest);
        var orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setOrderId(order.getId());
        orderDetail.setProduct(product.get());
        orderDetail.setProductId(productId);
        orderDetail.setQuantity(orderRequest.getQuantityProduct());
        orderDetail.setTotalPrice(orderDetail.getQuantity() * product.get().getPrice());
        order.setQuantityProduct(1);
        order.setTotalPriceOrder(orderDetail.getTotalPrice());
        orderDeatailRepository.save(orderDetail);
        orderRepository.save(order);
        List<AddOrderDetailResponse> list = new ArrayList<>();
        var orderDeatailResponse = modelMapper.map(orderDetail , AddOrderDetailResponse.class);
        list.add(orderDeatailResponse);
        var orderResponse = new AddOrderResponse(order.getOrderDate(), order.getName(),
               order.getAddress(), order.getNumber(), order.getStatus(),
                order.getQuantityProduct(), order.getTotalPriceOrder(), list);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<AddOrderResponse> checkOutFromCart(@RequestBody AddOrderRequest request){
        var order = orderService.generateOrder(request);
        var cart = cartRepository.findByUserId(order.getUser().getId()).get();
        var i = 0;
        List<AddOrderDetailResponse> list = new ArrayList<>();
        for (var p: cart.getProductCarts()) {
            i++;
            var orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setOrderId(order.getId());
            orderDetail.setProduct(p.getProduct());
            orderDetail.setProductId(p.getProduct().getId());
            orderDetail.setTotalPrice(p.getTotalprice());
            orderDetail.setQuantity(p.getQuantity());
            order.setTotalPriceOrder(order.getTotalPriceOrder() + p.getTotalprice());
            orderDeatailRepository.save(orderDetail);
            var orderDeatailResponse = modelMapper.map(orderDetail , AddOrderDetailResponse.class);
            list.add(orderDeatailResponse);
        }
        order.setQuantityProduct(i);
        orderRepository.save(order);
        var orderResponse = new AddOrderResponse(order.getOrderDate(), order.getName(),
                order.getAddress(), order.getNumber(), order.getStatus(),
                order.getQuantityProduct(), order.getTotalPriceOrder(), list);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/getList")
    public ResponseEntity<List<OrderDto>> getAll(){
        var listOrderResponse = orderService.findOrderByUserId().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrderResponse, HttpStatus.OK);
    }
    @GetMapping("/getListAll")
    public ResponseEntity<List<OrderDto>> getListAll(){
        var listOrder = orderService.findAllOrder().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
    @GetMapping("/getListOrderSuccess")
    public ResponseEntity<List<OrderDto>> getListOrderSuccess(){
        var listOrder = orderService.getListOrderByOrderSuccess().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
    @GetMapping("/getListOrderCancel")
    public ResponseEntity<List<OrderDto>> getListOrderCancel(){
        var listOrder = orderService.getListOrderByOrderCancel().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
    @GetMapping("/getListOrderComplete")
    public ResponseEntity<List<OrderDto>> getListOrderComplete(){
        var listOrder = orderService.getListOrderByOrderComplete().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
    @GetMapping("/getListOrderDelivery")
    public ResponseEntity<List<OrderDto>> getListOrderDelivery(){
        var listOrder = orderService.getListOrderByOrderDelivery().stream()
                .map(order -> modelMapper.map(order , OrderDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }
    @PutMapping("/updateStatus")
    public Order updateStatus( @RequestBody UpdateOrderRequest request){
        return orderService.updateStatus(request);
    }
    @GetMapping("/searchComplete")
    public ResponseEntity<List<OrderDto>> ListOrder(@RequestParam String name) {
        var listSearchOrder=orderService.listName("Đã Giao Thành Công",name).stream()
                .map(order -> modelMapper.map(order , OrderDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(listSearchOrder,HttpStatus.OK);
    }
    @GetMapping("/search_Waiting_For_Progressing")
    public ResponseEntity<List<OrderDto>> ListOrder1(@RequestParam String name) {
        var listSearchOrder=orderService.listName("Chờ xử lý",name).stream()
                .map(order -> modelMapper.map(order , OrderDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(listSearchOrder,HttpStatus.OK);
    }
    @GetMapping("/searchCancel")
    public ResponseEntity<List<OrderDto>> ListOrder2(@RequestParam String name) {
        var listSearchOrder=orderService.listName("Đã Hủy Đơn Hàng",name).stream()
                .map(order -> modelMapper.map(order , OrderDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(listSearchOrder,HttpStatus.OK);
    }
    @GetMapping("/searchDelivery")
    public ResponseEntity<List<OrderDto>> ListOrder3(@RequestParam String name) {
        var listSearchOrder=orderService.listName("Đang Giao Hàng",name).stream()
                .map(order -> modelMapper.map(order , OrderDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(listSearchOrder,HttpStatus.OK);
    }
    @GetMapping("/sum")
    public int sumMoney(){
        return orderService.sumMoney();
    }

}
