package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Author;


public interface AuthorRepository extends JpaRepository<Author,Long> {
	public Author findByEmail(String email);
	
	public Author findByName(String name);
}
