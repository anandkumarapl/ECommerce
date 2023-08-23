package com.springbootjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootjwt.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
