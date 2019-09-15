package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.example.demo.DTO.AuthorDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;

@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {
	
	@TestConfiguration
	static class AuthorServiceIntegrationTest {
		  
        @Bean
        public AuthorService authorService() {
            return new AuthorService();
        }
        
    }
	@Autowired
	private AuthorService authorService;
	
	@MockBean
	private BookRepository bookRepository;
	
	@MockBean
	private BookTagRepository bookTagRepository;
	
	@MockBean
	private AuthorRepository authorRepository;
	
	@BeforeEach
	public void setUp() {
		Author author = new Author();
		author.setName("Author Test");
		author.setEmail("AT@gmail.com");
		Author author2 = new Author();
		author2.setName("Author2 Test");
		author2.setEmail("AT2@gmail.com");
		Mockito.when(authorRepository.findByEmail(author.getEmail()))
        .thenReturn(author);
		Mockito.when(authorRepository.save(author))
        .thenReturn(author);
      	Mockito.when(authorRepository.findByName(author.getName()))
        .thenReturn(author);
      	Mockito.when(authorRepository.findAll())
        .thenReturn(Arrays.asList(author,author2));
	}
	
	@Test
    public void whenValidName_thenAuthorShouldBeFound() {
        String name = "Author Test";
        AuthorDTO found = authorService.findAuthor(name);      
         assertThat(found.getName())
          .isEqualTo(name);
     }
	
	@Test
    public void whenCreateAuthor_thenAuthorShouldBeCreated() {
        Author author=new Author();
		author.setName("Author Test");
		author.setEmail("UA@gmail.com");
        AuthorDTO found = authorService.createAuthor(author);
         assertThat(found.getName())
          .isEqualTo("Author Test");
     }
	
	@Test
    public void whenDeleteAuthor_thenAuthorShouldBeDeleted() {
		Author author=new Author();
        authorService.deleteAuthorById(author.getUserId());;
        verify(authorRepository, times(1)).deleteById((author.getUserId()));
     }
	
	@Test
    public void whenValidAuthorNames_thenAuthorsShouldBeFound() {
        String name = "Author Test";
        String name2 = "Author2 Test";
        java.util.List<AuthorDTO> authors=authorService.findAllAuthors();     
         assertThat(authors.get(0).getName())
          .isEqualTo(name);
         assertThat(authors.get(1).getName())
          .isEqualTo(name2);
     }
	
	@Test
	public void whenAuthorCreateBook_thenBookShouldBeCreated() {
		String name = "Author Test";
		BookDTO book = new BookDTO();
		book.setTitle("Book Test");
		BookDTO found = authorService.authorAddBook(name, book);
		assertThat(found.getAuthor())
        .isEqualTo("Author Test");
		
	}
	
	
}
