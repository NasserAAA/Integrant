package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.BookTagDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {
		
	@TestConfiguration
	static class BookServiceIntegrationTest {
		  
        @Bean
        public BookService bookService() {
            return new BookService();
        }
        
    }
	
	@Autowired
	private BookService bookService;
	
	@MockBean
	private BookRepository bookRepository;
	
	@MockBean
	private BookTagRepository bookTagRepository;
	
	@MockBean
	private AuthorRepository authorRepository;
	
	@BeforeEach
	 public void setUp() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setUniquetitle("Test Title Book By Unknown Author");
        Author author = new Author();
		  author.setName("Unknown Author");
		  author.setEmail("UA@gmail.com");
		  authorRepository.save(author);
		 book.setAuthor(author);
        Book book2 = new Book();
        book2.setTitle("Test Title2");
        book2.setUniquetitle("Test Title2 Book By Unknown Author");
        Author author2 = new Author();
		  author2.setName("Unknown Author2");
		  author2.setEmail("UA2@gmail.com");
		  authorRepository.save(author2);
		 book2.setAuthor(author2);
		 BookTag bookTag = new BookTag();
	     bookTag.setName("BookTag Test");
        Mockito.when(bookRepository.findByUniquetitle(book.getUniquetitle()))
          .thenReturn(book);
        Mockito.when(bookRepository.save(book))
          .thenReturn(book);
        Mockito.when(bookRepository.findAll())
          .thenReturn(Arrays.asList(book,book2));
        Mockito.when(bookTagRepository.findByName("BookTag Test"))
        .thenReturn(bookTag);
        Mockito.when(bookTagRepository.save(bookTag))
        .thenReturn(bookTag);
        Mockito.when(bookTagRepository.findAll())
        .thenReturn(Arrays.asList(bookTag));
        
      
    }
	
	@Test
	public void whenValidUniqueTitle_thenBookShouldBeFound() {
		  String title = "Test Title";
		  String author = "Unknown Author";
	        BookDTO found = bookService.findBookByTitle(title,author);     
	         assertThat(found.getUniquetitle())
	          .isEqualTo(title+" Book By "+author);
	}
	
	@Test
    public void whenCreateBook_thenBookShouldBeCreated() {
        Book book=new Book();
        book.setTitle("Book Test");
        book.setUniquetitle("Book Test Book By Author Test");
        Author author = new Author();
		  author.setName("Author Test");
		  author.setEmail("UA@gmail.com");
		  authorRepository.save(author);
		 book.setAuthor(author);
        BookDTO found = bookService.createBook(book);
         assertThat(found.getTitle())
          .isEqualTo("Book Test");
     }
	@Test
    public void whenDeleteBook_thenBookShouldBeDeleted() {
    	Book book=new Book();
        bookService.deleteBookById(book.getBookId());;
        verify(bookRepository, times(1)).deleteById((book.getBookId()));
     }
	
	@Test
    public void whenValidBookTitles_thenBooksShouldBeFound() {
        String title = "Test Title";
        String title2 = "Test Title2";
        java.util.List<BookDTO> books=bookService.findAllBooks();     
         assertThat(books.get(0).getTitle())
          .isEqualTo(title);
         assertThat(books.get(1).getTitle())
          .isEqualTo(title2);
     }
	
	@Test
	public void whenUpdateTags_thenTagsShouldBeUpdated() {
		String tag1 = "Drama";
		String tag2 = "Sci-Fi";
		ArrayList<String> tags = new ArrayList<>();
		tags.add(tag1);
		tags.add(tag2);
		String ut = "Test Title Book By Unknown Author";
		BookDTO book=bookService.updateBookTags(ut, tags);
		assertThat(book.getTags().get(0))
		.isEqualTo(tag1);
		assertThat(book.getTags().get(1))
		.isEqualTo(tag2);
		
	}
	
	@Test
    public void whenValidBookTagTitles_thenBookTagsShouldBeFound() {
        String title = "BookTag Test";
        java.util.List<BookTagDTO> tags=bookService.findAllTags();     
         assertThat(tags.get(0).getName())
          .isEqualTo(title);

     }
	
	@Test
    public void whenCreateBookTag_thenBookTagShouldBeCreated() {
        BookTag bookTag=new BookTag();
        bookTag.setName("Tag Test");
        BookTagDTO found = bookService.createTag(bookTag);
         assertThat(found.getName())
          .isEqualTo("Tag Test");
     }
	
	@Test
    public void whenDeleteTag_thenTagShouldBeDeleted() {
    	BookTag bookTag=new BookTag();
        bookService.deleteTagById(bookTag.getTagId());;
        verify(bookTagRepository, times(1)).deleteById((bookTag.getTagId()));
     }
	
}
