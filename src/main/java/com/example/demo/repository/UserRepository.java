package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserRecord;

public interface UserRepository extends JpaRepository<UserRecord, Long> {

	public UserRecord findByEmail(String email);
}
