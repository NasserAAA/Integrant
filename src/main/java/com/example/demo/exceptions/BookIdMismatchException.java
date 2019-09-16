package com.example.demo.exceptions;

public class BookIdMismatchException extends RuntimeException {
	 
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

	public BookIdMismatchException() {
		// TODO Auto-generated constructor stub
	}
   
}