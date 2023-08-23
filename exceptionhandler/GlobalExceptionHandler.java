package com.springbootjwt.exceptionhandler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springbootjwt.dto.ResponseDTO;
import com.springbootjwt.exception.*;
//handles all exceptions thrown by your code
@ControllerAdvice
public class GlobalExceptionHandler 
{
    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //used to handle the specific exceptions & sends the custom responses to the client
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ResponseDTO>handleBadRequestException(BadRequestException e)
    {
      logger.info("Bad Request Found {} ",e.getMsg(),e);
      return new ResponseEntity<>(
      	ResponseDTO.builder().responseMsg(e.getMsg()).build(),HttpStatus.BAD_REQUEST);
    }
    
    //use to handle the the exceptions if resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException ex, WebRequest req)
    {
    	ErrorDetails errors = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
    	return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
