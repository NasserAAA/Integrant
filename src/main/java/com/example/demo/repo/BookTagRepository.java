package com.example.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.BookTag;

public interface BookTagRepository extends JpaRepository<BookTag,Long> {
	public BookTag findByName(String name);
}
