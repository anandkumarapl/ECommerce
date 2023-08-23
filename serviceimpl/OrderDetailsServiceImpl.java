package com.springbootjwt.serviceimpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootjwt.exception.BadRequestException;
import com.springbootjwt.exception.ResourceNotFoundException;
import com.springbootjwt.model.OrderDetails;
import com.springbootjwt.model.Product;
import com.springbootjwt.model.User;
import com.springbootjwt.repository.OrderDetailsRepository;
import com.springbootjwt.repository.UserRepository;
import com.springbootjwt.repository.productRepository;
import com.springbootjwt.service.OrderDetailsService;

//allows to add business functionalities
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService
{
	//@Autowired used for automatic dependency injection
	@Autowired
	private productRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
	 // method to get the period between the cancelDate and the orderDate   
	static int find(LocalDate  orderDate, LocalDate cancelDate)   
	    {   
	        Period difference = Period.between(orderDate, cancelDate);   
	        return difference.getDays(); 
	    }  
	
	@Override
	//implementing an interface method for PaymentMode
	public void checkOut(OrderDetails order) 
	{
		String payMode = order.getPaymentMode();
		if(!payMode.equalsIgnoreCase("COD"))
		{
			throw new BadRequestException("Sorry for the inconvinience, we only have Cash on Delivery payment mode.");
		}
	}
	
	@Override
	//implementing an interface method to place an Order
	public String placeOrder(int productId, long userId, OrderDetails orderDetails) 
	{
		  String message = null;
		  Product p = productRepository.findById(productId).orElseThrow(()->
		  new ResourceNotFoundException("Product", "ID", productId));
		  User u=userRepository.findById(userId).orElseThrow(()->
		  new ResourceNotFoundException("User", "ID", userId));
		  if(p.getStock()!=0)
		  {
		  if(p.getStock()>orderDetails.getQuantity()) 
		  {
		  orderDetails.setTotal(p.getPrice()* orderDetails.getQuantity());
		  orderDetails.setProduct(p);
		  orderDetails.setUser(u);
		  orderDetails.setStatus("ORDER PLACED");
		  checkOut(orderDetails);
		  orderDetails.setPaymentMode("COD");
		  p.setStock(p.getStock()-orderDetails.getQuantity());
		  productRepository.save(p);
		  orderDetailsRepository.save(orderDetails);
		  //parsing message on placing order
		  message="*****\tYour order has been placed successfully!!\t*****"+""
		  		  +"\nCustomer Name: "+u.getName()
		  		  +"\nORDER ID: "+orderDetails.getOrderId()
		  		  +"\nProduct Type: "+p.getProductType()
		  		  +"\nProduct Name: "+p.getName()
		  		  +"\nPer Product Price: "+p.getPrice()
		  		  +"\nProduct Quantity: "+orderDetails.getQuantity()
		  		  +"\nPayment Mode: "+orderDetails.getPaymentMode()
				  +"\n\t\t\t\t\t\tTotal amount: "+orderDetails.getTotal()+""
		  		  +"\n\t\t Your product will be delivered within 7 working days ";
		  }
		  else 
		  {
			  message="You can order only "+p.getStock()+" items";
		  }
		  }
		  else
		  {
			  p.setStatus("OUT OF STOCK");
			  productRepository.save(p);
			  message="Product is out of stock";
		  }
		  return message;
	}

	
	//interface method to cancel an placed Order
	@Override
	public String cancelOrder(int orderId) 
	{
		String message;
		LocalDate cancelDate=LocalDate.now();
		//manual date set 
//		LocalDate cancelDate=LocalDate.of(2023,07,03);
		OrderDetails orderObj=orderDetailsRepository.findById(orderId).get();
		LocalDate orderDate=orderObj.getOrderDate();
		int days=find(orderDate,cancelDate);
		System.out.println(days);
		if(days<=7)
			{
			//update stock
			Product p=orderObj.getProduct();
			p.setStock(p.getStock()+orderObj.getQuantity());
			productRepository.save(p);
			
			orderObj.setStatus("CANCELLED");
			orderDetailsRepository.save(orderObj);
			//Cancel order
			//orderDetailsRepository.delete(orderObj);
			message="Order has been cancelled successfully";
			}
			else
			{
				message="You can't cancel order after 7 days";
			}
		return message;
	}

	
	@Override
	//implementing an interface method to get an order details
	public String getOrderDetails(int orderId) 
	{
		OrderDetails orderDetails =orderDetailsRepository.findById(orderId).orElseThrow(()->
		new ResourceNotFoundException("Order Details", "Id", orderId));
		return 
		"\t\t\t ***** ORDER DETAILS *****"+""
		+"\nCustomer Name: "+orderDetails.getUser().getName()
		+"\nCustomer Email Id : "+orderDetails.getUser().getEmailAddress()
		+"\nCustomer Address: "+orderDetails.getUser().getAddress()
  		+"\nPayment Mode: "+orderDetails.getPaymentMode()
  		+"\nOrder Status: "+orderDetails.getStatus()
		+"\n\n*****Your order details******"
  		+"\nProduct Type: "+orderDetails.getProduct().getProductType()
  		+"\nProduct Name: "+orderDetails.getProduct().getName()
  		+"\nPer Product Price: "+orderDetails.getProduct().getPrice()
  		+"\nProduct Quantity: "+orderDetails.getQuantity()
  		+"\nTotal amount: "+orderDetails.getTotal()
  		+"\nPayment Mode: COD  ( Cash On Delivery )"+"";
	
	}

	@Override
	public List<OrderDetails> getAllOrder() {
		
		return orderDetailsRepository.findAll();
	}	
}
