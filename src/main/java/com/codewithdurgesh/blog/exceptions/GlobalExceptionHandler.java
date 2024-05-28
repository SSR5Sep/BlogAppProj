package com.codewithdurgesh.blog.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithdurgesh.blog.payloads.ApiResponse;



@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
	String status = ex.getMessage();
	
	ApiResponse apiResponse = new ApiResponse(status, false);
	return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String, String> res=new HashMap<>();
		//ex.getBindingResult().getAllErrors().forEach((error)->{
			ex.getBindingResult().getAllErrors().forEach((errors)->{;
			String fieldError=((FieldError)errors).getField();
			String message = errors.getDefaultMessage();
			res.put(fieldError, message);
			
		});
		
		return new ResponseEntity<Map<String,String>>(res,HttpStatus.BAD_REQUEST);
		
		}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> ApiExceptionHandler(ApiException ex){
	String status = ex.getMessage();
	
	ApiResponse apiResponse = new ApiResponse(status, true);
	return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	
	}
	



