package com.example.demo.exceptions;

public class AuthorNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

	public AuthorNotFoundException() {
		// TODO Auto-generated constructor stub
	}
}
