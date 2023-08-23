package com.springbootjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootjwt.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
