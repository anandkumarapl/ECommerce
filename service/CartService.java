package com.springbootjwt.service;

import java.util.List;

import com.springbootjwt.model.Cart;
import com.springbootjwt.model.CartItem;

public interface CartService 
{

	Cart getCartByCartId(int cartId);
	
	Cart validate(int cartId);
	
	void update(Cart cart);
	
	List<CartItem> getAllCartItems(int cartId);
}
