 

package com.springbootjwt.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootjwt.dto.ResponseDTO;
import com.springbootjwt.model.OrderDetails;
import com.springbootjwt.model.Product;
import com.springbootjwt.model.User;
import com.springbootjwt.service.ProductService;
import com.springbootjwt.serviceimpl.ProductServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

//to build REST API 
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController
{
	//used for automatic dependency injection
	@Autowired
	private ProductService productService;
	
	//Annotation for mapping HTTP POST request
    //@PostMapping("/admin/addProduct")
	@PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product)
    { 	
    	return new ResponseEntity<Product>(productService.addProduct(product),HttpStatus.CREATED);   	
    }
   
    //Annotation for mapping HTTP GET request
    @GetMapping("/productsByType/{productType}")
    public List<Product> getAllProductsByName(@PathVariable("productType") String productType)
    {
    	return  productService.getAllproductsByProductType(productType);
    }
  
    //Annotation for mapping HTTP PUT request
    @PutMapping("updateProduct/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) 
    {
    	return productService.updateProduct(id, product);
    }
    
    //Annotation for mapping HTTP PUT request
    @PutMapping("cancelProduct/{id}") 
	public void cancelProduct(@PathVariable("id") int id)
	{
		//returns the cancellation status
		 productService.cancelProduct(id);
	}
    
    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
    	return productService.getAllproducts();
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") int id )
    {
    	return productService.getProductById(id);
    }
}

