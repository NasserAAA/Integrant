package com.example.demo.exceptionhandler;

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