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

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
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
	
	@DeleteMapping("/deleteBook/{bookTitle}/{authorName}")
	public void authorDeleteBook(@PathVariable String bookTitle, @PathVariable String authorName ) {
		 authorService.authorDeleteBook(bookTitle, authorName);
	}
	
	@GetMapping("/AllBooks/{name}")
	public ArrayList<BookDTO> findAllBooksByAuthor(@PathVariable String name) {
	 return authorService.findAllBooksByAuthor(name);
	 }
	
	
}
