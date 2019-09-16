package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class EmailExistsException extends Exception {
	public EmailExistsException(String message)
	{
		super(message);
	}

	public EmailExistsException() {
		super();
	}
}
