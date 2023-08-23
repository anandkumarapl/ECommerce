package com.springbootjwt.service;

import org.springframework.http.ResponseEntity;

import com.springbootjwt.model.Cart;
import com.springbootjwt.model.CartItem;

public interface CartItemService 
{
	ResponseEntity<String> addCartItem(CartItem cartItem, int cartId, int productId);
	
	ResponseEntity<String> removeCartItem(int cartItemId);
	
	ResponseEntity<String> removeAllCartItems(int cartId);
}
