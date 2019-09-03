package com.example.demo.exceptionhandler;

public class BookTagNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookTagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

	public BookTagNotFoundException() {
		// TODO Auto-generated constructor stub
	}
}
