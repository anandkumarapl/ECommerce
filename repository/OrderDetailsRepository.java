package com.springbootjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springbootjwt.model.OrderDetails;

//creating repository interface extends JpaRepository 
//inherits a set of CRUD (Create, Read, Update, Delete) operations 
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>
	{

	}
