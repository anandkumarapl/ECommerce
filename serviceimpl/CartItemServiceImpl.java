package com.springbootjwt.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springbootjwt.model.Cart;
import com.springbootjwt.model.CartItem;
import com.springbootjwt.model.Product;
import com.springbootjwt.model.User;
import com.springbootjwt.repository.CartItemRepository;
import com.springbootjwt.repository.CartRepository;
import com.springbootjwt.repository.UserRepository;
import com.springbootjwt.repository.productRepository;
import com.springbootjwt.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	productRepository productRepository; 
	
	
	@Override
	public ResponseEntity<String> addCartItem(CartItem cartItem, int cartId, int productId) {
		
		Cart cart=cartRepository.findById(cartId).get();
		Product product =productRepository.findById(productId).get();
		cartItem.setPrice(product.getPrice()*cartItem.getQuantity());
		cartItem.setProduct(product);
		cartItem.setCart(cart);
		
		double amt = cartItem.getPrice() + cart.getTotalPrice();
			
		cart.setTotalPrice(amt);
		List<CartItem> cartItems=cart.getCartItem();
		cartItems.add(cartItem);
		cart.setCartItem(cartItems);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
		return new ResponseEntity<String>("Item added to cart.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> removeCartItem(int cartItemId) {
		CartItem cartItem =cartItemRepository.findById(cartItemId).get();
		Cart cart =cartItem.getCart();
		cart.setTotalPrice(cart.getTotalPrice()-cartItem.getPrice());
		List<CartItem> cartItems =cart.getCartItem();
		cartItems.remove(cartItem);
		cartRepository.save(cart);
		cartItemRepository.deleteById(cartItemId);
		return new ResponseEntity<String>("Item removed from cart.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> removeAllCartItems(int cartId) {
		
		Cart cart =cartRepository.findById(cartId).get();
		List<CartItem> cartItems=cart.getCartItem();
		for(CartItem c: cartItems)
		{
			removeCartItem(c.getId());
		}
		//cartItems.clear();

		//cart.setTotalPrice(0.0);
		
		//cartRepository.save(cart);
		return new ResponseEntity<String>("All items removed from cart.", HttpStatus.OK);
	}

}
