package com.springbootjwt.serviceimpl;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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
import com.springbootjwt.service.ProductService;

//allows to add business functionalities
@Service
public class ProductServiceImpl implements ProductService
{
	//@Autowired used for automatic dependency injection
	@Autowired
	private productRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
	
	@Override
	//implementing an interface method to add an product into system
	public Product addProduct(Product product)
	{
		product.setStatus("IN STOCK");
		return productRepository.save(product);
	}
	
	
	@Override
	//implementing an interface method to add all product into system
	public List<Product> getAllproductsByProductType(String productType) 
	{
		List<Product> products=productRepository.getProductByProductType(productType);	
		return products;
	}

	
	@Override
	// Updates the details of a product with the given ID
	public Product updateProduct(int id, Product product) 
	{
		Product Product = productRepository.findById(id).orElseThrow(()->
		new BadRequestException("Product with this id not found"));
		Product.setName(product.getName());
		Product.setProductType(product.getProductType());
		Product.setStock(product.getStock());
		Product.setPrice(product.getPrice());
		Product.setStatus("IN STOCK");
		productRepository.save(Product);
		return Product;
	}

	@Override
	//Canceling the details of a product with the given ID
	public String cancelProduct(int id) 
	{
		String message=null;
		
		//retrieves the booking from the productRepository using the ID
		Product products= productRepository.findById(id).get();
		if(products!=null)
		{
			//productRepository.deleteById(id);
			products.setStatus("OUT OF STOCK");
			products.setStock(0);
			productRepository.save(products);
			message="Production of product has been stopped";
		}
		else
		{
			message="Product Id not found";
		}
		return message;
	}


	@Override
	public List<Product> getAllproducts() {
		return productRepository.findAll();
	}


	@Override
	public Product getProductById(int id) {
		Product product = productRepository.findById(id).orElseThrow(()->
		new BadRequestException("Product with this id not found"));
		return product;
	}
}
