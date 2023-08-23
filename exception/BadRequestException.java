package com.springbootjwt.exception;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

//A runtime exception indicating a bad client request
public class BadRequestException extends RuntimeException
{
	//for error message
    private String msg;

}
