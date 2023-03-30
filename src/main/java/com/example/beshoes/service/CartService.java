package com.example.beshoes.service;



import com.example.beshoes.models.Cart;
import com.example.beshoes.models.request.AddToCartRequest;
import com.example.beshoes.models.request.UpdateCartRequest;

import java.util.Optional;

public interface CartService {
    Optional<Cart> findCartByUserId();


    Cart addToCart(AddToCartRequest request);

    Cart findCartByUser();

    String updateCart(UpdateCartRequest request);

    boolean deleteFormCart(long productId);


}
