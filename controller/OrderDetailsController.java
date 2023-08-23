package com.springbootjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootjwt.model.OrderDetails;
import com.springbootjwt.model.User;
import com.springbootjwt.repository.OrderDetailsRepository;
import com.springbootjwt.repository.UserRepository;
import com.springbootjwt.service.OrderDetailsService;

//to build REST API 
@RestController
//@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderDetailsController
{
	//@Autowired used for automatic dependency injection
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailRepository;
	
	//Annotation for mapping HTTP POST request 
    @PostMapping("/placeOrder/productId/{pId}/userId/{uId}") 
    public void  placeOrder(@PathVariable("pId") int productId,@PathVariable("uId") long userId,@RequestBody OrderDetails orderDetails) 
    {
    	User user=userRepository.findById(userId).get();
    	if(user.getRole().getName().equals("guest")) 
    	{
    		orderDetailsService.placeOrder(productId, userId, orderDetails);
    		//return new ResponseEntity<String>(orderDetailsService.placeOrder(productId, userId, orderDetails),HttpStatus.OK);}
    	}
    		
    	else
    	{
    		//return new ResponseEntity<String>("Only User can place Order",HttpStatus.BAD_REQUEST);
    	}
    }
   	
    //Annotation for mapping HTTP PUT request
    @PutMapping("/cancelOrder/{oId}")
    public void cancelOrder(@PathVariable("oId") int orderId)
    {
    	OrderDetails order = orderDetailRepository.findById(orderId).get();
    	User user = userRepository.findById(order.getUser().getId()).get();
    	if(user.getRole().getName().equals("guest")) 
    	{
    		orderDetailsService.cancelOrder(orderId);
    		//return new ResponseEntity<String>(orderDetailsService.cancelOrder(orderId),HttpStatus.OK);
    	}
//    	else
//    	{
//    		return new ResponseEntity<String>("Only User can cancel Order",HttpStatus.BAD_REQUEST);
//    	}
    }
    
    @GetMapping("/getOrderDetails/{id}")
    public String getOrderDetails(@PathVariable("id") int orderId)
    {
    	return orderDetailsService.getOrderDetails(orderId);
    }
    
    @GetMapping("/getAllOrders")
    public List<OrderDetails> getAllOrder()
    {
    	return orderDetailsService.getAllOrder();
    }
}
