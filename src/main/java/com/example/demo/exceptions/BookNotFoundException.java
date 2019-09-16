package com.example.demo.exceptions;

public class BookNotFoundException extends RuntimeException {
	 


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

	public BookNotFoundException() {
		// TODO Auto-generated constructor stub
	}
   
}