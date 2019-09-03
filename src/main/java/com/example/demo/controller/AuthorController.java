package com.example.demo.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.DTO.AuthorDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.services.AuthorService;


@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@GetMapping
	public ArrayList<AuthorDTO> findAll() {
	 return authorService.findAllAuthors();
	 }
	
	@GetMapping("/email/{email}")
    public AuthorDTO findByEmail(@PathVariable String email) {
        return authorService.findAuthorByEmail(email);
    }
	
	@GetMapping("/{name}")	
    public AuthorDTO findAuthor(@PathVariable String name) {
        return authorService.findAuthor(name);
    }
	
	@PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public AuthorDTO createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }
	
	@DeleteMapping("/{name}")
    public void deleteAuthor(@PathVariable String name) {
    	authorService.deleteAuthor(name);
    	
    }
	
	@PutMapping("/{name}")
    public AuthorDTO updateAuthor(@RequestBody Author author, @PathVariable String name) {
      return  authorService.updateAuthor(author,name);
          
    }
	
	@PostMapping("/createBook/{name}")
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO authorAddBook(@RequestBody BookDTO book, @PathVariable String name) {
		return authorService.authorAddBook(name, book);
	}
	
	@DeleteMapping("/deleteBook/{book_id}/{author_id}")
	public void authorDeleteBook(@PathVariable Long book_id, @PathVariable Long author_id ) {
		 authorService.authorDeleteBook(author_id, book_id);
	}
	
	@GetMapping("/AllBooks/{name}")
	public ArrayList<BookDTO> findAllBooksByAuthor(@PathVariable String name) {
	 return authorService.findAllBooksByAuthor(name);
	 }
	
	@PutMapping("/updateBook/{book_id}/{author_id}")
	public BookDTO authorUpdateBook(@RequestBody Book book, @PathVariable Long book_id, @PathVariable Long author_id) {
		return authorService.authorUpdateBook(author_id, book_id, book);
	}
	
}
