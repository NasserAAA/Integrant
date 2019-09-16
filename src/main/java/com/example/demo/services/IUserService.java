package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.EmailExistsException;
import com.example.demo.exceptions.PasswordsNotMatchingException;
import com.example.demo.model.UserRecord;

public interface IUserService {
	UserRecord registerNewUserAccount(UserDto accountDto) 
		      throws EmailExistsException, PasswordsNotMatchingException;
}