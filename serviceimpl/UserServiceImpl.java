
package com.springbootjwt.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootjwt.exception.*;
import com.springbootjwt.dto.LoginDTO;
import com.springbootjwt.dto.UserDTO;
import com.springbootjwt.model.Address;
import com.springbootjwt.model.Cart;
import com.springbootjwt.model.Role;
import com.springbootjwt.model.User;
import com.springbootjwt.repository.RoleRepository;
import com.springbootjwt.repository.UserRepository;
import com.springbootjwt.service.UserService;
import com.springbootjwt.util.JwtUtil;

import java.util.List;
import java.util.Optional;

//allows to add business functionalities
@Service

//specifies the semantics of the transactions
@Transactional
public class UserServiceImpl implements UserService
{
	//@Autowired used for automatic dependency injection
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil; 

    
    @Override
    //creating method for user register
    public UserDTO registerUser(UserDTO userDTO) 
    { 
    	List<User> users=userRepository.findAll();
    	for(User u : users)
    	{
    		if(u.getEmailAddress().equals(userDTO.getEmailAddress()))
    		{
    			throw new BadRequestException("User already exists.. Please provide new details.");
    		}
    	}
    	//For retrieving the role of an login client into the system
    	Role role=roleRepository.findById(2l).get();
    	Address address = Address.builder().building(userDTO.getAddress().getBuilding())
    			.city(userDTO.getAddress().getCity())
    			.state(userDTO.getAddress().getState())
    			.pincode(userDTO.getAddress().getPincode())
    			.build();
    	Cart cart = Cart.builder().totalPrice(0.0).build();
    	User user=User.builder()
      				.name(userDTO.getName())
        			.address(address)
                	.emailAddress(userDTO.getEmailAddress())
                	.phone(userDTO.getPhone())
                    .role(role)
                    .cart(cart)
                    .password(passwordEncoder.encode
                    		(userDTO.getPassword()))
                	.build();
        userRepository.save(user);
        return userDTO;
    }

    
    @Override
    //method for login
    public String login(LoginDTO loginDTO) 
    {
    	//provide method used to check the presence of value for particular variable
        Optional<User> userOptional=
        	userRepository.findByEmailAddress(loginDTO.getEmailAddress());
         
        if(userOptional.isEmpty())
        {
        	//if login credentials not found
            throw new BadRequestException("User Not Found.");
        }
        if(passwordEncoder.matches(loginDTO.getPassword(),userOptional.get().getPassword()))
        {
        	//if email password matches JWT Token generates
            return jwtUtil.generateAccessToken
            		(userOptional.get());
        }
        else
        {
        	//if any one the the required credential matches and another credential detail dosn't matches
           throw new BadRequestException("Invalid Email Or Password");
        }
    }
}
