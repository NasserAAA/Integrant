package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserRecord;
import com.example.demo.model.ConfirmationToken;

public interface ConfirmationTokenRepository 
extends JpaRepository<ConfirmationToken, Long> {

  ConfirmationToken findByConfirmationToken(String token);

  ConfirmationToken findByUser(UserRecord user);
}