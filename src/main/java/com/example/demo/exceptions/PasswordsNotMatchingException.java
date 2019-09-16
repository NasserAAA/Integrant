package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class PasswordsNotMatchingException extends Exception {
	public PasswordsNotMatchingException(String message)
	{
		super(message);
	}
	public PasswordsNotMatchingException()
	{
		super();
	}
}
