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
 
    @DeleteMapping("/{booktitle}/{author}")
    public void delete(@PathVariable String booktitle, @PathVariable String author ) {
    	bookService.deleteBook(booktitle,author);
    	
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id ) {
    	bookService.deleteBookById(id);
    	
    }
    
    @PutMapping("/inStock/{uniqueTitle}/{stock}")
    public BookDTO updateinStock(@PathVariable String uniqueTitle , @PathVariable int stock) {
    	return bookService.updateinStock(uniqueTitle,stock);
    }
    
    @PutMapping("/outStock/{uniqueTitle}")
    public BookDTO updateoutStock(@PathVariable String uniqueTitle) {
    	return bookService.updateoutStock(uniqueTitle);
    }
    
    
    @PutMapping("/updateTagsinBook/{uniqueTitle}")
    public BookDTO updateBookTags(@PathVariable String uniqueTitle, @RequestBody ArrayList<String> tags) {
      return  bookService.updateBookTags(uniqueTitle,tags);      
    }	
    
    @GetMapping("/findAllTagsinBook/{uniqueTitle}")
    public ArrayList<BookTagDTO> findAllTagsinBook(@PathVariable String uniqueTitle) {
        return bookService.findAllTagsinBook(uniqueTitle);
    }
    
    /*
     * 
     * 
     * BookTag Controller
     * 
     * 
     */
	
	 @GetMapping("/tags")
	    public ArrayList<BookTagDTO> findAllTags() {
	        return bookService.findAllTags();
	    }
	 
	 @PostMapping("/tags")
	    @ResponseStatus( HttpStatus.CREATED)
	    public BookTagDTO create(@RequestBody BookTag booktag) {
	        return bookService.createTag(booktag);
	    }
	 
	 @DeleteMapping("/tags/{name}")
	    public void deleteTag(@PathVariable String name) {
	    	bookService.deleteTag(name);
	    	
	    }
	 @GetMapping("/findAllBooksinTag/{name}")
	    public ArrayList<BookDTO> findAllBooksinTag(@PathVariable String name) {
	        return bookService.findAllBooksinTag(name);
	    }
	 
	 
}

