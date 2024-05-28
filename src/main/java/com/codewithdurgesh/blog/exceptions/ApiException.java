package com.codewithdurgesh.blog.exceptions;

public class ApiException extends RuntimeException {

	// constructor with parameter
	public ApiException(String message) {
		super(message);
		
	}
	//default constructor without parameter
	public ApiException() {
		super();
		
	}
	

}
