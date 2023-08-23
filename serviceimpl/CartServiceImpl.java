package com.springbootjwt.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootjwt.exception.ResourceNotFoundException;
import com.springbootjwt.model.Cart;
import com.springbootjwt.model.CartItem;
import com.springbootjwt.repository.CartRepository;
import com.springbootjwt.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;
	
	@Override
	public Cart getCartByCartId(int cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(()->
		new ResourceNotFoundException("Cart", "Id", cartId));
		return cart;
	}

	@Override
	public Cart validate(int cartId) {
		
		return null;
	}

	@Override
	public void update(Cart cart) {
		

	}

	@Override
	public List<CartItem> getAllCartItems(int cartId) {
		Cart cart =cartRepository.findById(cartId).get();
		return cart.getCartItem();
	}

}
