package com.springbootjwt.service;

import java.util.List;

import com.springbootjwt.model.OrderDetails;

//This Service interface class holds  business logical through an interface methods 
public interface OrderDetailsService 
{
	//for placing an order
	String	placeOrder(int productId,long userId,OrderDetails orderDetails);
	
	//for canceling an placed order
	String cancelOrder(int orderId);
	
	//for getting PaymentMode
	void checkOut(OrderDetails order);
	
	//for getting Placed Order Details
	String getOrderDetails(int orderId);
	
	List<OrderDetails> getAllOrder();
}
