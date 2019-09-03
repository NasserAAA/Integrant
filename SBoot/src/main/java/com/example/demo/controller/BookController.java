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
import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.BookTagDTO;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
   	 @Autowired
     private BookService bookService;
	
	
	 
    @GetMapping
    public ArrayList<BookDTO> findAll() {
        return bookService.findAllBooks();
    }
 
    @GetMapping("/title/{bookTitle}/{author}")
    public BookDTO findByuniqueTitle(@PathVariable String bookTitle , @PathVariable String author) {
        return bookService.findBookByTitle(bookTitle,author);
    }
 
    @GetMapping("/{id}")	
    public BookDTO findOne(@PathVariable Long id) {
        return bookService.findBook(id);
    }
 
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public BookDTO create(@RequestBody Book book) {
        return bookService.createBook(book);
    }
 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	bookService.deleteBook(id);
    	
    }
 
    @PutMapping("/{id}")
    public BookDTO updateBook(@RequestBody Book book, @PathVariable Long id) {
      return  bookService.updateBook(book,id);
          
    }
    
    @PutMapping("/inStock/{id}/{stock}")
    public BookDTO updateinStock(@PathVariable Long id , @PathVariable int stock) {
    	return bookService.updateinStock(id,stock);
    }
    
    @PutMapping("/outStock/{id}")
    public BookDTO updateoutStock(@PathVariable Long id) {
    	return bookService.updateoutStock(id);
    }
    @PutMapping("/updateTaginBook/{BookId}/{id}")
    public BookDTO updateBookTag(@PathVariable Long BookId, @PathVariable Long id) {
      return  bookService.updateBookTag(BookId,id);
          
    }
    
	
	 @GetMapping("/tags")
	    public ArrayList<BookTagDTO> findAllTags() {
	        return bookService.findAllTags();
	    }
	 
	 @PostMapping("/tags")
	    @ResponseStatus( HttpStatus.CREATED)
	    public BookTagDTO create(@RequestBody BookTag booktag) {
	        return bookService.createTag(booktag);
	    }
	 
	 @DeleteMapping("/tags/{id}")
	    public void deleteTag(@PathVariable Long id) {
	    	bookService.deleteTag(id);
	    	
	    }
	 
	 @PutMapping("/tags/updateBookinTag/{TagId}/{id}")
	    public BookTagDTO updateTagBook(@PathVariable Long TagId, @PathVariable Long id) {
	      return  bookService.updateTagBook(TagId,id);
	          
	    }
}

