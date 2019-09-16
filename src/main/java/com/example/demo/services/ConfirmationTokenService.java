package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserRecord;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.model.ConfirmationToken;

@Service
public class ConfirmationTokenService {
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationToken createConfirmationToken(UserRecord registered) {
		return confirmationTokenRepository.save(new ConfirmationToken(registered));
	}

	public ConfirmationToken getConfirmationToken(String confirmationToken) {
		return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	}
	public Optional<ConfirmationToken> getConfirmationTokenById(long id) {
		return confirmationTokenRepository.findById(id);
	}
	public List<ConfirmationToken> getAllConfirmationTokens() {
		 List<ConfirmationToken>confirmationTokens = new ArrayList<>();  
		 confirmationTokenRepository.findAll().forEach(confirmationTokens::add);  
	        return confirmationTokens;
	}
	public void updateToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}
}
