package com.springbootjwt.util;

//JWT Validation
//JWT Token generation
//JWT Token Parsing
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springbootjwt.dto.JwtTokenDTO;
import com.springbootjwt.exception.BadRequestException;
import com.springbootjwt.model.User;
import java.util.Date;

//allows spring to detect custom beans automatically
@Component
public class JwtUtil 
{
    private final Logger logger= LoggerFactory.getLogger(JwtUtil.class);
    private final String jwtSecret = "testsecret";
    private final String jwtIssuer = "test_issuer";
   
    //method for access of generated jwt token for 1 week
    public String generateAccessToken(User user) 
    {
        return Jwts.builder()
                .setSubject(user.getEmailAddress())
                .setIssuer(jwtIssuer)
                .claim("ROLE", user.getRole().getName())
                .claim("SUBJECT_ID",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                	new Date(System.currentTimeMillis()+ 
                    7 * 24 * 60 * 60 * 1000)) // 1 week   ( 7 days * 24 hrs * 60 mins * 60 sec * 1000 milisec )
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    //methods to validate and parse the JWT token
    //Throw And Handle Below Exceptions Under Global Exception Handler
    public boolean validate(String token) 
    {
    	try 
    	{
    		Jwts.parser()
    		.setSigningKey(jwtSecret)  //using the secret key used to sign the token
    		.parseClaimsJws(token);
    		return true;
    	} 
    	catch(SignatureException ex) {
    		logger.error("Invalid JWT token - {}", ex.getMessage());
    		throw new BadRequestException("Invalid JWT Token");
    	}
    	catch (MalformedJwtException ex)
    	{
    		logger.error("Invalid JWT token - {}", ex.getMessage());
    	} 
    	catch (ExpiredJwtException ex)
    	{
    		logger.error("Expired JWT token - {}", ex.getMessage());
    	} catch (UnsupportedJwtException ex) 
    	{
    		logger.error("Unsupported JWT token - {}",
    		ex.getMessage());
    	} 
    	catch (IllegalArgumentException ex)
    	{
    		logger.error("JWT claims string is empty - {}",
			ex.getMessage());
    	}
    		return false;
    }

    public JwtTokenDTO getJwtTokenDTO(String token)
    {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token).getBody();
    	return JwtTokenDTO.builder().subject(claims.getSubject())
    			.expirationDate(claims.getExpiration())
    			.role(claims.get("ROLE", String.class))
    			.build();
    }
}