package com.springbootjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootjwt.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
