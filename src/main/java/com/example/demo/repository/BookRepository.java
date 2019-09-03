package com.example.demo.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
   public Book findByUniquetitle(String uniquetitle);
   
   public List<Book> findAllBooksByAuthor(Author author);
}