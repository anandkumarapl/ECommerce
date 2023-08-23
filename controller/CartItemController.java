package com.springbootjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootjwt.model.CartItem;
import com.springbootjwt.service.CartItemService;

@RestController
public class CartItemController {

	@Autowired
	CartItemService cartItemService;
	
	@PostMapping("/addCartItem/{cartId}/{productId}")
	public ResponseEntity<String> addCartItem(@RequestBody CartItem cartItem,
			@PathVariable("cartId") int cartId,@PathVariable("productId") int productId)
	{
		return cartItemService.addCartItem(cartItem, cartId, productId);
	}
	
	@DeleteMapping("removeItem/{id}")
	public ResponseEntity<String> removeItem(@PathVariable("id") int cartItemId)
	{
		return cartItemService.removeCartItem(cartItemId);
	}
	
	@DeleteMapping("removeAllItems/{id}")
	public ResponseEntity<String> removeAllItems(@PathVariable("id") int cartId)
	{
		return cartItemService.removeAllCartItems(cartId);
	}
}
